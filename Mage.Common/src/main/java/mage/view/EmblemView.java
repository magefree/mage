package mage.view;

import mage.cards.Card;
import mage.game.command.Emblem;
import mage.players.PlayableObjectStats;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * @author noxx
 */
public class EmblemView implements CommandObjectView, Serializable {

    protected UUID id;
    protected String name;
    protected String expansionSetCode;
    protected List<String> rules;
    protected PlayableObjectStats playableStats = new PlayableObjectStats();

    public EmblemView(Emblem emblem) {
        this.id = emblem.getId();
        this.name = emblem.getName();
        this.expansionSetCode = emblem.getExpansionSetCode();
        this.rules = emblem.getAbilities().getRules(emblem.getName());
    }

    @Override
    public String getExpansionSetCode() {
        return expansionSetCode;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public List<String> getRules() {
        return rules;
    }

    @Override
    public boolean isPlayable() {
        return this.playableStats.getPlayableAmount() > 0;
    }

    @Override
    public void setPlayableStats(PlayableObjectStats playableStats) {
        this.playableStats = playableStats;
    }

    @Override
    public PlayableObjectStats getPlayableStats() {
        return this.playableStats;
    }

    @Override
    public boolean isChoosable() {
        // unsupported
        return false;
    }

    @Override
    public void setChoosable(boolean isChoosable) {
        // unsupported
    }

    @Override
    public boolean isSelected() {
        // unsupported
        return false;
    }

    @Override
    public void setSelected(boolean isSelected) {
        // unsupported
    }
}
