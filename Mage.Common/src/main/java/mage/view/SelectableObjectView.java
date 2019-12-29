package mage.view;

/**
 * @author JayDi85
 */
public interface SelectableObjectView {

    boolean isPlayable();

    void setPlayable(boolean isPlayable);

    void setPlayableAmount(int playableAmount);

    int getPlayableAmount();

    boolean isChoosable();

    void setChoosable(boolean isChoosable);

    boolean isSelected();

    void setSelected(boolean isSelected);
}
