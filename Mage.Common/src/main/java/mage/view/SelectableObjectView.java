package mage.view;

import mage.players.PlayableObjectStats;

/**
 * @author JayDi85
 */
public interface SelectableObjectView {

    boolean isPlayable();

    void setPlayableStats(PlayableObjectStats playableStats);

    PlayableObjectStats getPlayableStats();

    boolean isChoosable();

    void setChoosable(boolean isChoosable);

    boolean isSelected();

    void setSelected(boolean isSelected);
}
