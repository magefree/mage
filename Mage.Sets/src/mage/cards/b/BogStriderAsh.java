package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author EvilGeek
 */
public final class BogStriderAsh extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Goblin spell");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public BogStriderAsh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.TREEFOLK, SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());

        // Whenever a player casts a Goblin spell, you may pay {G}. If you do, you gain 2 life.
        this.addAbility(new SpellCastAllTriggeredAbility(new DoIfCostPaid(new GainLifeEffect(2), new ManaCostsImpl<>("{G}")), filter, false));
    }

    private BogStriderAsh(final BogStriderAsh card) {
        super(card);
    }

    @Override
    public BogStriderAsh copy() {
        return new BogStriderAsh(this);
    }
}
