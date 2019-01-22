package uma.caosd.cvl.m2m;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import bvr.BaseModelHandle;

/**
 * This serves as an aditional model with the parameters that need to be passed to the model transformation.
 * These parameters are the references from the variation point to the base model (BaseHandleModel), each BaseHandleModel constains an identifier (id):
 * target, source, optionalObject, optionalLink, etc. depending on the kind of variation point, and a reference to the specific element in the base model (MOFref). 
 * 
 * @author Josemi
 *
 */
public class XMLParamsModel {
	// Namespaces
	private static final String XMI_VERSION = "version";
	private static final String XMI_VERSION_VALUE = "2.0";
	private static final String XMLNS_XMI = "xmi";
	private static final String XMLNS_XMI_VALUE = "http://www.omg.org/XMI";
	private static final String XMLNS_XSI = "xsi";
	private static final String XMLNS_XSI_VALUE = "http://www.w3.org/2001/XMLSchema-instance";
	private static final String XMLNS_CVLPARAMS = "cvlparams";
	private static final String XMLNS_CVLPARAMS_VALUE = "cvlparams";

	// Atributtes
	private static final String XSI_SCHEMALOCATION = "schemaLocation";
	private static final String XSI_SCHEMALOCATION_VALUE = "cvlparams CVLParams.ecore";

	// Elements
	private static final String ROOT_ELEMENT = "CVLParams";
	private static final String PARAMS = "params";
	private static final String VALUE = "value";
	private static final String KEY = "key";
	
	private static Namespace xsi;
	private static Namespace xmi;
	private static Namespace cvlparams;

	public static void generateConfigXML(List<BaseModelHandle> refs, String filepath) {
		try {
			Element root = generateRootElement();
			Document doc = new Document(root);
			
			for (BaseModelHandle bmh : refs) {
				Element config = new Element(PARAMS);
				config.setAttribute(new Attribute(KEY, bmh.getId()));
				config.setAttribute(new Attribute(VALUE, bmh.getMOFRef()));
				doc.getRootElement().addContent(config);
			}
			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(filepath)); // filepath example: "c:\\file.xml"
		} catch (IOException io) {
			String desc = "Error generating CVLParams model. IOException.";
			io.printStackTrace();
		}
	}

	private static Element generateRootElement() {
		generateNamespaces();
		Element beans = new Element(ROOT_ELEMENT, cvlparams);

		beans.addNamespaceDeclaration(xsi);
		beans.addNamespaceDeclaration(xmi);
		beans.addNamespaceDeclaration(cvlparams);

		beans.setAttribute(XSI_SCHEMALOCATION, XSI_SCHEMALOCATION_VALUE, xsi);
		return beans;
	}

	private static void generateNamespaces() {
		xsi = Namespace.getNamespace(XMLNS_XSI, XMLNS_XSI_VALUE);
		xmi = Namespace.getNamespace(XMLNS_XMI, XMLNS_XMI_VALUE);
		cvlparams = Namespace.getNamespace(XMLNS_CVLPARAMS, XMLNS_CVLPARAMS_VALUE);
	}
}
