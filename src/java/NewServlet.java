import org.w3c.dom.Node;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.StringWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author Walaa Soudy
 */
@WebServlet(urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    try {
//        // Check for delete action
//        String action = request.getParameter("action");

        String numStudentsParam = request.getParameter("numStudents");

        if (numStudentsParam != null && !numStudentsParam.isEmpty()) {
            int numStudents = Integer.parseInt(numStudentsParam);

            // Build XML document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc;

            // Check if the file exists for appending or create a new document
            File file = new File("C:\\Users\\ALkamel\\Documents\\NetBeansProjects\\WebApplication١\\src\\java\\university.xml");
            if (file.exists()) {
                // Parse the existing XML file
                doc = docBuilder.parse(file);
            } else {
                // Create a new XML document
                doc = docBuilder.newDocument();
                Element universityElement = doc.createElement("University");
                doc.appendChild(universityElement);
            }

            // Take student data from the user and store it in the XML document
            for (int i = 0; i < numStudents; i++) {
                String studentID = request.getParameter("id" + i);
                String firstName = request.getParameter("firstName" + i);
                String lastName = request.getParameter("lastName" + i);
                String gender = request.getParameter("gender" + i);
                String gpa = request.getParameter("gpa" + i);
                String level = request.getParameter("level" + i);
                String address = request.getParameter("address" + i);

                // Create Student element
                Element studentElement = doc.createElement("Student");
                studentElement.setAttribute("ID", studentID);
                doc.getDocumentElement().appendChild(studentElement);

                // Add child elements for student data
                createElementAndAppendText(doc, studentElement, "FirstName", firstName);
                createElementAndAppendText(doc, studentElement, "LastName", lastName);
                createElementAndAppendText(doc, studentElement, "Gender", gender);
                createElementAndAppendText(doc, studentElement, "GPA", gpa);
                createElementAndAppendText(doc, studentElement, "Level", level);
                createElementAndAppendText(doc, studentElement, "Address", address);
            }

            // Save the XML document to a file (you may want to adjust the file path)
            saveXmlDocument(doc, "C:\\Users\\ALkamel\\Documents\\NetBeansProjects\\WebApplication١\\src\\java\\university.xml");

            out.println("XML document created and data stored successfully!");
        }
    } catch (ParserConfigurationException | NumberFormatException e) {
        e.printStackTrace();
        out.println("Error occurred: " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Error occurred: " + e.getMessage());
    } finally {
        out.close();
    }
}


    private void createElementAndAppendText(Document doc, Element parent, String elementName, String textContent) {
        Element element = doc.createElement(elementName);
        element.appendChild(doc.createTextNode(textContent));
        parent.appendChild(element);
    }


    private void saveXmlDocument(Document doc, String fileName) {
       try {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(fileName);
        transformer.transform(source, result);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
}