package kemzang.store.store.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import kemzang.store.store.dto.EmployeDTO;
import kemzang.store.store.model.Departement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import kemzang.store.store.service.DepartementService;
import kemzang.store.store.service.EmployeService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/departement")
public class DepartementController {

    private final DepartementService departementService;
    private final EmployeService employeService;

    // Liste des départements
    @GetMapping
    public String getAllDepartements(Model model) {
        model.addAttribute("departements", departementService.allDepartements());
        return "departement/list";
    }

    /* Créer un nouveau département : la création et
       la création d'une entité aura toujours 2 méthodes
       (une avec GetMapping pour le formulaire d'ajout et
       l'autre avec PostMapping pour traiter les données saisies via le formulaire */
    @GetMapping("/new")
    public String add(Model model) {
        model.addAttribute("departement", new Departement());
        return "departement/add";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("departement") Departement departement,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes ra) {
        // On teste si les données saisies dans le formulaire sont validées
        if (bindingResult.hasErrors()) {
            // Retourner à la vue avec les erreurs
            return "departement/add";
        }

        try {
            departementService.addDepartement(departement);
            // Message flash
            String msg = "Le département " + departement.getLibelleDepartement() + " a été créé avec succès!";
            ra.addFlashAttribute("msg", msg);

            // Après la création d'un nouveau département, on se redirige vers la page des listes des départements
            return "redirect:/departement";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("libelleDepartement", null, e.getMessage());
            return "departement/add";
        }
    }

    // Pour voir la liste des employés pour le département indiqué
    @GetMapping("/employes/{id}")
    public String listeEmployesByPoste(@PathVariable UUID id, Model model) {
        Departement departement = departementService.getDepartementById(id)
                .orElseThrow(() -> new EntityNotFoundException("Département non existant"));

        // Liste des employés du département dont l'id est {id}
        List<EmployeDTO> employesDTO = employeService.getAllEmployeesByDepartement(id);

        // Transmission des variables au template
        model.addAttribute("departement", departement.getLibelleDepartement());
        model.addAttribute("employes", employesDTO);

        // Affichage du template
        return "departement/listeemploye";
    }

    /* Modifier les informations d'un département : la création et
       la modification d'une entité aura toujours 2 méthodes
       (une avec GetMapping pour le formulaire de modification et
       l'autre avec PostMapping pour traiter les données saisies via le formulaire */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable UUID id, Model model, RedirectAttributes ra) {
        // On récupère le département à modifier
        Departement departement = departementService.getDepartementById(id)
                .orElseThrow(() -> new EntityNotFoundException("Département non trouvé"));

        model.addAttribute("departement", departement);

        // Appel de la page qui affichera les informations sur le département à modifier
        return "departement/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable UUID id, @Valid @ModelAttribute("departement") Departement departement,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes ra) {
        // On teste si les données saisies dans le formulaire sont validées
        if (bindingResult.hasErrors()) {
            // Retourner à la vue avec les erreurs
            return "departement/edit";
        }

        try {
            departementService.updateDepartement(id, departement);
            // Message flash
            String msg = "Le département " + departement.getLibelleDepartement() + " a été mis à jour avec succès!";
            ra.addFlashAttribute("msg", msg);

            // Après la modification d'un département, on se redirige vers la page des listes des départements
            return "redirect:/departement";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("libelleDepartement", null, e.getMessage());
            return "departement/edit";
        }
    }

    // Supprimer un département de la BD
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id, Model model, RedirectAttributes ra) {
        // Suppression du département dont l'id est {id}
        departementService.deleteDepartement(id);

        // Message flash
        String msg = "Le département a été supprimé avec succès!";
        ra.addFlashAttribute("msg", msg);

        // Redirection vers la page affichant les listes des départements
        return "redirect:/departement";
    }
}
