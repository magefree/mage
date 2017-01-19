package mage.client.cards;

import mage.view.CardView;

import java.awt.event.MouseEvent;
import java.util.Collection;

/**
 * Created by StravantUser on 2016-09-22.
 */
public interface DragCardTarget {
    void dragCardEnter(MouseEvent e);
    void dragCardMove(MouseEvent e);
    void dragCardExit(MouseEvent e);
    void dragCardDrop(MouseEvent e, DragCardSource source, Collection<CardView> cards);
}
