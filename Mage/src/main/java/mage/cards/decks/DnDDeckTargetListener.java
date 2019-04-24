package mage.cards.decks;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class DnDDeckTargetListener extends DropTargetAdapter {

    private static final transient Logger logger = Logger.getLogger(DnDDeckTargetListener.class);
    private static final DataFlavor fileFlavor = DataFlavor.javaFileListFlavor;
    private static final DataFlavor plainTextFlavor = DataFlavor.stringFlavor;

    private boolean isCopyOrMove(int dropAction) {
        return (dropAction & TransferHandler.COPY_OR_MOVE) != 0;
    }

    private boolean isCopyAction(int dropAction) {
        return (dropAction & TransferHandler.COPY) != 0;
    }

    private boolean isMoveAction(int dropAction) {
        return (dropAction & TransferHandler.MOVE) != 0;
    }

    private boolean isAcceptable(DropTargetDragEvent dtde) {
        boolean copyOrMove = isCopyOrMove(dtde.getDropAction());
        boolean flavorSupported = dtde.isDataFlavorSupported(plainTextFlavor) || dtde.isDataFlavorSupported(fileFlavor);
        return copyOrMove && flavorSupported;
    }

    private boolean isAcceptable(DropTargetDropEvent dtde) {
        boolean copyOrMove = isCopyOrMove(dtde.getDropAction());
        boolean flavorSupported = dtde.isDataFlavorSupported(plainTextFlavor) || dtde.isDataFlavorSupported(fileFlavor);
        return copyOrMove && flavorSupported;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        if (isAcceptable(dtde)) {
            dtde.acceptDrag(TransferHandler.COPY);
        } else {
            dtde.rejectDrag();
        }
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        if (isAcceptable(dtde)) {
            dtde.acceptDrag(TransferHandler.COPY);
        } else {
            dtde.rejectDrag();
        }
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        if (isAcceptable(dtde)) {
            dtde.acceptDrag(TransferHandler.COPY);
        } else {
            dtde.rejectDrag();
        }
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        if (isAcceptable(dtde)) {
            dtde.acceptDrop(TransferHandler.COPY);
        } else {
            dtde.rejectDrop();
            dtde.dropComplete(false);
            return;
        }

        boolean move = isMoveAction(dtde.getDropAction());

        try {
            if (dtde.isDataFlavorSupported(fileFlavor)) {
                List<File> files = (List<File>) dtde.getTransferable().getTransferData(fileFlavor);
                dtde.dropComplete(handleFilesDrop(move, files));
            } else if (dtde.isDataFlavorSupported(plainTextFlavor)) {
                String text = (String) dtde.getTransferable().getTransferData(plainTextFlavor);
                try {
                    dtde.dropComplete(handleUriDrop(move, new URI(text)));
                } catch (URISyntaxException e) {
                    dtde.dropComplete(handlePlainTextDrop(move, text));
                }
            }
        } catch (UnsupportedFlavorException | IOException e) {
            logger.error("Unsupported drag and drop data", e);
            dtde.dropComplete(false);
        }
    }

    protected boolean handlePlainTextDrop(boolean move, String text) {
        return false;
    }

    protected boolean handleUriDrop(boolean move, URI uri) {
        return false;
    }

    protected boolean handleFilesDrop(boolean move, List<File> files) {
        return false;
    }

}
