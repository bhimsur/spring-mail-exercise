package com.example.springemail.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.util.Map;

public class ThymeleafUtil {
    public static TemplateEngine templateEngine(String directory) {
        FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
        fileTemplateResolver.setPrefix(directory);
        fileTemplateResolver.setTemplateMode(TemplateMode.HTML);
        fileTemplateResolver.setCharacterEncoding("UTF-8");
        fileTemplateResolver.setCacheable(false);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(fileTemplateResolver);

        return templateEngine;
    }

    /**
     * Fill parameter in html file
     * @param directory directory file exist
     * @param templateName file name
     * @param context data
     * @return String html file
     */
    public static String processHtmlToString(String directory, String templateName, Context context) {
        return ThymeleafUtil.templateEngine(directory).process(templateName, context);
    }

    /**
     * Fill parameter in html file
     * @param directory directory file exist
     * @param templateName file name
     * @param obj Dto class
     * @return String html file
     */
    public static String processHtmlToString(String directory, String templateName, Object obj) {
        Map<String, Object> data = ThymeleafUtil.convertObjectToMap(obj);
        return processHtmlToString(directory, templateName, data);
    }

    /**
     * Fill parameter in html file
     * @param directory directory file exist
     * @param templateName file name
     * @param data Map<String, Object> parameter data
     * @return String html file
     */
    public static String processHtmlToString(String directory, String templateName, Map<String, Object> data) {
        Context context = new Context();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }

        return processHtmlToString(directory, templateName, context);
    }

    /**
     * Fill parameter in html file
     * @param obj dto class
     * @return Map<String, Object> parameter data
     */
    public static Map<String, Object> convertObjectToMap(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = mapper.convertValue(obj, Map.class);
        return data;
    }
}
