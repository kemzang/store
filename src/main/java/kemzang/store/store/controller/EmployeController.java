package kemzang.store.store.controller;

import kemzang.store.store.model.Employe;
import kemzang.store.store.service.DepartementService;
import kemzang.store.store.service.PosteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import kemzang.store.store.service.EmployeService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employe")
public class EmployeController {

    private final EmployeService employeService;
    private final DepartementService departementService;
    private final PosteService posteService;

    // Liste de tous les employés
    @GetMapping
    public String employes(Model model) {
        model.addAttribute("employes", employeService.getAllEmployes());
        return "employe/list";
    }

    /* Créer un nouvel employé : la création et
       la modification d'une entité aura toujours 2 méthodes
       (une avec GetMapping pour le formulaire ajout/modification et
       l'autre avec PostMapping pour traiter les données saisies
       via le formulaire */
    @GetMapping("/new")
    public String addEmploye(Model model) {
        model.addAttribute("employe", new Employe());
        model.addAttribute("postes", posteService.getAllPostes());
        model.addAttribute("departements", departementService.allDepartements());
        return "employe/add";
    }

    // Sauvegarde des données saisies du formulaire
    @PostMapping("/save")
    public String saveEmploye(@Valid @ModelAttribute("employe") Employe employe,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes ra) {
        model.addAttribute("departements", departementService.allDepartements());
        model.addAttribute("postes", posteService.getAllPostes());

        // On teste si les données saisies dans le formulaire sont validées
        if (bindingResult.hasErrors()) {
            return "employe/add"; // Retourner à la vue avec les erreurs
        }

        try {
            employeService.addEmploye(employe);
            // Message flash
            String msg = "L'employé " + employe.getNom() + " a été créé avec succès!";
            ra.addFlashAttribute("msg", msg);

        /* Après la création d'un employé
           on se redirige vers la page des listes des employés */
            return "redirect:/employe";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("nom", null, e.getMessage());
            return "employe/add";
        }
    }

    // Afficher les détails d'un employé
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable UUID id, Model model) {
        model.addAttribute("employe", employeService.getEmployeById(id));
        return "employe/detail";
    }

    /* Modification des infos d'employé : la création et
       la modification d'une entité aura toujours 2 méthodes
       (une avec GetMapping pour le formulaire ajout/modification et
       l'autre avec PostMapping pour traiter les données saisies
       via le formulaire */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable UUID id, Model model, RedirectAttributes ra) {
        // On récupère l'employé à modifier
        model.addAttribute("employe", employeService.getEmployeById(id));
        model.addAttribute("postes", posteService.getAllPostes());
        model.addAttribute("departements", departementService.allDepartements());

        return "employe/edit";
    }

    // Sauvegarde des données saisies du formulaire
    @PostMapping("/update/{id}")
    public String update(@PathVariable UUID id,
                         @Valid @ModelAttribute("employe") Employe employe,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes ra) {
        // On teste si les données saisies dans le formulaire sont validées
        if (bindingResult.hasErrors()) {
            // Retourner à la vue avec les erreurs
            return "employe/edit";
        }

        try {
            employeService.updateEmploye(id, employe);
            // Message flash
            String msg = "L'employé " + employe.getNom() + " a été modifié avec succès!";
            ra.addFlashAttribute("msg", msg);

        /* Après la modification d'un employé
           on se redirige vers la page des listes des employés */
            return "redirect:/employe";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("nom", null, e.getMessage());
            return "employe/add";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id, Model model, RedirectAttributes ra) {
        // Suppression de l'employé dont l'id est {id}
        employeService.deleteEmploye(id);

        // Message flash
        String msg = "L'employé a été supprimé avec succès!";
        ra.addFlashAttribute("msg", msg);

        // Redirection vers la page affichant les listes des employés
        return "redirect:/employe";
    }
}
