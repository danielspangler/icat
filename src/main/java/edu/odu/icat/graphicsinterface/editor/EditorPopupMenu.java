package edu.odu.icat.graphicsinterface.editor;

import com.mxgraph.swing.mxGraphComponent;
import edu.odu.icat.graphicsinterface.editor.EditorActions.HistoryAction;
import edu.odu.icat.graphicsinterface.editor.EditorActions.NewVertexAction;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxResources;


import javax.swing.*;

public class EditorPopupMenu extends JPopupMenu
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3132749140550242191L;

	public EditorPopupMenu(BasicGraphEditor editor)
	{
		boolean selected = !editor.getGraphComponent().getGraph()
				.isSelectionEmpty();

        //add(editor.bind("Test", new EditorActions.TestAction()));
        add(editor.bind(mxResources.get("newVertex"), new NewVertexAction()));
        
        addSeparator();

        add(editor.bind(mxResources.get("zoomIn"), mxGraphActions.getZoomInAction()));
        add(editor.bind(mxResources.get("zoomOut"), mxGraphActions.getZoomOutAction()));
        add(editor.bind(mxResources.get("actualSize"),mxGraphActions.getZoomActualAction()));

		//add(editor.bind(mxResources.get("undo"), new HistoryAction(true),
		//		"/com/mxgraph/examples/swing/images/undo.gif"));

		addSeparator();

		add(
				editor.bind(mxResources.get("cut"), TransferHandler
						.getCutAction(),
						"/com/mxgraph/examples/swing/images/cut.gif"))
				.setEnabled(selected);
		add(
				editor.bind(mxResources.get("copy"), TransferHandler
						.getCopyAction(),
						"/com/mxgraph/examples/swing/images/copy.gif"))
				.setEnabled(selected);
		add(editor.bind(mxResources.get("paste"), TransferHandler
				.getPasteAction(),
				"/com/mxgraph/examples/swing/images/paste.gif"));

		addSeparator();

		add(
				editor.bind(mxResources.get("delete"), new EditorActions.DeleteVertexAction(),
						"/com/mxgraph/examples/swing/images/delete.gif"))
				.setEnabled(selected);

		//addSeparator();

		// Creates the format menu
		//JMenu menu = (JMenu) add(new JMenu(mxResources.get("format")));

		//EditorMenuBar.populateFormatMenu(menu, editor);

		// Creates the shape menu
		//menu = (JMenu) add(new JMenu(mxResources.get("shape")));

		//EditorMenuBar.populateShapeMenu(menu, editor);

		//addSeparator();

		//add(
		//		editor.bind(mxResources.get("edit"), mxGraphActions
		//				.getEditAction())).setEnabled(selected);

		//addSeparator();

		//add(editor.bind(mxResources.get("selectVertices"), mxGraphActions
		//		.getSelectVerticesAction()));
		//add(editor.bind(mxResources.get("selectEdges"), mxGraphActions
		//		.getSelectEdgesAction()));

		//addSeparator();

		//add(editor.bind(mxResources.get("selectAll"), mxGraphActions
		//		.getSelectAllAction()));
	}

}
