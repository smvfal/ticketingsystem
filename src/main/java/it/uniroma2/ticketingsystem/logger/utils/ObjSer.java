package it.uniroma2.ticketingsystem.logger.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

import static it.uniroma2.ticketingsystem.logger.utils.PersistenceUtils.initializeAndUnproxy;
import static org.hibernate.proxy.HibernateProxyHelper.getClassWithoutInitializingProxy;

public abstract class ObjSer {


    public static String serializeObject(Object object) throws Throwable {
        String[] params = null;
        String[] idParams = null;

        params = ReflectUtils.getParameters(object);
        idParams = ReflectUtils.getIDParameters(object);


        //String objectId ="";

        String serializedObject;

        if(params == null){
            // serializza tutti i parametri dell oggetto
            if(idParams==null){
                //objectId = "no id";
                serializedObject = ObjSer.objToJson(object);
            }else{
                //objectId = ObjSer.buildIDJson(object, idParams);
                serializedObject = ObjSer.objToJson(object);
            }

        }else{
            // serializza solo alcuni attributi dell'oggetto
            //objectId = ObjSer.buildIDJson(object, idParams);
            serializedObject = ObjSer.buildJson(object, params);
        }

        return serializedObject;
    }

    public static String objToJson(Object object){

        String jsonString=null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            objectMapper.setDateFormat(sdf);

            //  mapper.writeValue(System.out, object);

            jsonString = objectMapper.writeValueAsString(object);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*
        System.out.print("----------------");
        System.out.println(jsonString);
        */
        return jsonString;

    }


    public static String buildJson(Object object, String[] attributes) throws Throwable {

        String[] idParams = ReflectUtils.getIDParameters(object);
        String id = getIDJsonString(object, idParams);

        String t, st = "{ ";

        if(!id.equals(null)){
            st.concat(id + " ,\n ");
        }

        int i , l = attributes.length;

        for (i = 0; i < l - 1; i++) {
            String value = ReflectUtils.fieldToString(object, attributes[i]);
            if(value!= null) {
                t = "\"" + attributes[i] + "\": \"" + ReflectUtils.fieldToString(object, attributes[i]) + "\",\n ";
                st = st.concat(t);
            }
        }
        String value = ReflectUtils.fieldToString(object, attributes[i]);
        if(value!= null) {
            t = "\"" + attributes[i] + "\": \"" + ReflectUtils.fieldToString(object, attributes[i]) + "\" ";
            st = st.concat(t);
        }
        st=st.concat(" } ");
        // System.out.println("\n\n*******" + st + "*******");

        return st;
    }


    public static String buildIDJson(Object object, String[] attributes) throws Throwable{

        String st="{ ";

        String s = getIDJsonString(object,attributes);

        st = st.concat(s);
        st = st.concat(" }");
        return st;

    }


    public static String getIDJsonString(Object object, String[] attributes) throws Throwable {

        String t, st = "";

        if(attributes == null)
            return "NA";


        int l = attributes.length;
        int i = 0;

        Class objectClass = getClassWithoutInitializingProxy(object);
        Object obj = initializeAndUnproxy(object);

        if(attributes[0].equals("")){
            return "NA";
        }

        while (i < l - 1) {
            try {
                Field field = FieldUtils.getField(objectClass, attributes[i], true);
                System.err.println("field: " + field.toString());
                // TODO: check su tipo dell'oggetto
                t = "\"" + attributes[i] + "\": \"" + field.get(obj) + "\",\n ";
                st = st.concat(t);
            }
            catch (NullPointerException e){
                System.err.println("Attenzione: Attributo \"" + attributes[i] + "\" non trovato nella classe \"" + objectClass.getClass().getName() + "\"");
            }
            i++;
        }
        try {
            Field field_attr = FieldUtils.getField(objectClass, attributes[i], true);
            t = "\"" + attributes[i] + "\": \"" + field_attr.get(obj) + "\"";

            st = st.concat(t);
        }catch (NullPointerException e){
            System.err.println("Attenzione: Attributo \"" + attributes[i] + "\" non trovato nella classe \"" + objectClass.getClass().getName() + "\"");
        }

        return st;
    }

    public static String objectsToJson(String[] objs, String[] inputArgs){
        String mergedJson="";
        for (int i=0; i<inputArgs.length;++i){
            if (i==0){
                mergedJson+= "{";
            }
            String json_i = objs[i];
            mergedJson += "\n \t '"+inputArgs[i]+"' : "+ json_i ;
            if (i<inputArgs.length-1){
                mergedJson+=",";
            }else {
                mergedJson += "\n }";
            }

        }
        return mergedJson;
    }



}