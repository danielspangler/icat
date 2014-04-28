/**
 * $Id: EditorKeyboardHandler.java,v 1.1 2012/11/15 13:26:46 gaudenz Exp $
 * Copyright (c) 2008, Gaudenz Alder
 */
package edu.odu.icat.graphicsinterface.editor;

import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;

import javax.swing.*;

/**
 * @author Administrator
 * 
 */
public class EditorKeyboardHandler extends mxKeyboardHandler
{

	/**
	 * 
	 * @param graphComponent
	 */
	public EditorKeyboardHandler(mxGraphComponent graphComponent)
	{
		super(graphComponent);
	}

	/**
	 * Return JTree's input map.
	 */
	protected InputMap getInputMap(int condition)
	{
		InputMap map = super.getInputMap(condition);

		if (condition == JComponent.WHEN_FOCUSED && map != null)
		{
//			map.put(KeyStroke.getKeyStroke("control S"), "save");
//			map.put(KeyStroke.getKeyStroke("control shift S"), "saveAs");
//			map.put(KeyStroke.getKeyStroke("control N"), "new");
//			map.put(KeyStroke.getKeyStroke("control O"), "open");

			//map.put(KeyStroke.getKeyStroke("control Z"), "undo");
			//map.put(KeyStroke.getKeyStroke("control Y"), "redo");
			//map
			//		.put(KeyStroke.getKeyStroke("control shift V"),
			//				"selectVertices");
			map.put(KeyStroke.getKeyStroke("control shift E"), "selectEdges");
            map.remove(KeyStroke.getKeyStroke("control V"));
            map.remove(KeyStroke.getKeyStroke("control C"));
            map.remove(KeyStroke.getKeyStroke("control X"));
            map.remove(KeyStroke.getKeyStroke("DELETE"));
            map.put(KeyStroke.getKeyStroke("DELETE"), "delete");
		}

		return map;
	}

	/**
	 * Return the mapping between JTree's input map and JGraph's actions.
	 */
	protected ActionMap createActionMap()
	{
		ActionMap map = super.createActionMap();

		map.put("save", new EditorActions.SaveAction(false));
		map.put("saveAs", new EditorActions.SaveAction(true));
		map.put("new", new EditorActions.NewAction());
		map.put("open", new EditorActions.OpenAction());
		map.put("undo", new EditorActions.HistoryAction(true));
		map.put("redo", new EditorActions.HistoryAction(false));
		map.put("selectVertices", mxGraphActions.getSelectVerticesAction());
		map.put("selectEdges", mxGraphActions.getSelectEdgesAction());
        map.put("delete", new EditorActions.DeleteVertexAction());

		return map;
	}

}
