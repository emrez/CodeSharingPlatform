package com.platform.codesharing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@Controller
public class CodeSnippetController {
    private final CodeSnippetService snippetService;

//    private final ModelMapper modelMapper;

    @Autowired
    public CodeSnippetController(CodeSnippetService snippetService) {
        this.snippetService = snippetService;
    }

    @GetMapping(value = "/api/code/latest", produces = "application/json")
    public ResponseEntity<ArrayList<ModelMap>> getApiCode() {
        ArrayList<CodeSnippet> snippets = snippetService.getLatest();
        ArrayList<ModelMap> models = new ArrayList<>();

        snippets.forEach(snippet -> {
            ModelMap model = new ModelMap();
            model.addAttribute("views", snippet.getViews());
            model.addAttribute("time", snippet.getTime());
            model.addAttribute("code", snippet.getCode());
            model.addAttribute("date", snippet.getFormattedDate());
            models.add(model);
        });
        return new ResponseEntity<>(models, HttpStatus.OK);
    }


    @GetMapping(value = "/api/code/{uuid}", produces = "application/json")
    public ResponseEntity<Model> getApiCode(Model model, @PathVariable String uuid) {
        ResponseEntity<Model> response;
        try {
            CodeSnippet snippet = snippetService.findByUuid(uuid);
            addToModel(model, snippet);
            response = ResponseEntity.ok(model);
            System.out.println(snippet);
        } catch (Exception e) {
            System.out.println("UB CATCH: " + e);
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @GetMapping(value = "/code/latest")
    public String getCode(Model model) {
        ArrayList<CodeSnippet> snippets = snippetService.getLatest();

        model.addAttribute("title", "Latest");
        model.addAttribute("snippets", snippets);
        model.addAttribute("empty", snippets.isEmpty());
//        model.addAttribute("time_restricted", false);
//        model.addAttribute("view_restricted", false);
        return "code";
    }

    @GetMapping(value = "/code/{uuid}")
    public Object getCode(Model model, @PathVariable String uuid) {
        try {
            CodeSnippet snippet = snippetService.findByUuid(uuid);
            ArrayList<CodeSnippet> snippets = new ArrayList<>();
            snippets.add(snippet);

            model.addAttribute("title", "Code");
            model.addAttribute("snippets", snippets);
            model.addAttribute("empty", snippets.isEmpty());
//            model.addAttribute("timeRestricted", snippet.isSecretByTime());
//            model.addAttribute("viewRestricted", snippet.isSecretByView());
            return "code";
        } catch (Exception e) {
            System.out.println("UBC CATCH: " + e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/api/code/new", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createNewCode(@RequestBody CodeSnippet codeSnippet) {
        codeSnippet = snippetService.save(codeSnippet);
        return ResponseEntity.ok(String.format("{ \"id\": \"%s\" }", codeSnippet.getUuid()));
    }


    @GetMapping(value = "/code/new", produces = "text/html")
    public String getNewCodePage() {
        return "new-code-snippet";
    }

    public void addToModel(Model model, CodeSnippet snippet) {
        model.addAttribute("views", snippet.getViews());
        model.addAttribute("time", snippet.getTime());
        model.addAttribute("code", snippet.getCode());
        model.addAttribute("date", snippet.getFormattedDate());
    }
}
