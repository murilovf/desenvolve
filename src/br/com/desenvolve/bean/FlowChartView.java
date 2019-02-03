package br.com.desenvolve.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;
 
@ManagedBean(name = "diagramFlowChartView")
@RequestScoped
public class FlowChartView {
     
    private DefaultDiagramModel model;
 
    @PostConstruct
    public void init() {
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
         
        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        model.setDefaultConnector(connector);
         

         
        Element trouble = new Element("Pai", "15em", "5em");
        trouble.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));

         
        Element itemMae = new Element("Mãe", "45em", "5em");
        itemMae.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));

         
        Element fail = new Element("Filho", "30em", "20em");
        fail.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
        fail.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
        fail.setStyleClass("ui-diagram-fail");
                         
        model.addElement(trouble);
        model.addElement(itemMae);
        model.addElement(fail);
                 

        model.connect(createConnection(trouble.getEndPoints().get(0), fail.getEndPoints().get(0), "Pai"));
        model.connect(createConnection(itemMae.getEndPoints().get(0), fail.getEndPoints().get(1), "Mãe"));
    }
     
    public DiagramModel getModel() {
        return model;
    }
     
    private Connection createConnection(EndPoint from, EndPoint to, String label) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(20, 20, 1, 1));
         
        if(label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.5));
        }
         
        return conn;
    }
}