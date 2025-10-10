package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class QueryUtil {
    private static final Map<String, String> queries = new HashMap<>();

    static {
        loadQueries();
    }

    private static void loadQueries() {
        try {
            // 자바로 xml을 가져오면 String으로 변환
            InputStream inputStream = QueryUtil.class.getClassLoader().getResourceAsStream("queries.xml");

            if (inputStream == null) {
                throw new RuntimeException("queries.xml not found");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder(); // xml, html의 구조를 이해할 수 있는 인스턴스 생성
            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("query");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element queryElement = (Element) nodeList.item(i);
                String key = queryElement.getAttribute("key");
                String sql = queryElement.getTextContent().trim();
                queries.put(key, sql);
            }
        } catch (Exception ex) {
            throw new RuntimeException("쿼리 로딩 중 오류 발생", ex);
        }

    }

    public static String getQuery(String key) {
        return queries.get(key);
    }
}
