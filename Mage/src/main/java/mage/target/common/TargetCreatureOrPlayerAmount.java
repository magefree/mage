package mage.target.common;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentOrPlayer;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCreatureOrPlayerAmount extends TargetPermanentOrPlayerAmount {

    private static final FilterPermanentOrPlayer defaultFilter
            = new FilterPermanentOrPlayer("creatures and/or players");

    static {
        defaultFilter.getPermanentFilter().add(CardType.CREATURE.getPredicate());
    }

    public TargetCreatureOrPlayerAmount(int amount) {
        // 107.1c If a rule or ability instructs a player to choose “any number,” that player may choose
        // any positive number or zero, unless something (such as damage or counters) is being divided
        // or distributed among “any number” of players and/or objects. In that case, a nonzero number
        // of players and/or objects must be chosen if possible.
        this(StaticValue.get(amount));
        this.minNumberOfTargets = 1;
    }

    public TargetCreatureOrPlayerAmount(DynamicValue amount) {
        super(amount);
        this.zone = Zone.ALL;
        this.filter = defaultFilter;
        this.targetName = filter.getMessage();
    }

    private TargetCreatureOrPlayerAmount(final TargetCreatureOrPlayerAmount target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public TargetCreatureOrPlayerAmount copy() {
        return new TargetCreatureOrPlayerAmount(this);
    }
}
