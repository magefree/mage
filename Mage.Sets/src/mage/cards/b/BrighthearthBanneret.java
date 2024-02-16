package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.ReinforceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author North
 */
public final class BrighthearthBanneret extends CardImpl {

    private static final FilterCard filter = new FilterCard("Elemental spells and Warrior spells");

    static {
        filter.add(Predicates.or(
                SubType.ELEMENTAL.getPredicate(),
                SubType.WARRIOR.getPredicate()));
    }

    public BrighthearthBanneret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.ELEMENTAL, SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Elemental spells and Warrior spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // Reinforce 1-{1}{R}
        this.addAbility(new ReinforceAbility(1, new ManaCostsImpl<>("{1}{R}")));
    }

    private BrighthearthBanneret(final BrighthearthBanneret card) {
        super(card);
    }

    @Override
    public BrighthearthBanneret copy() {
        return new BrighthearthBanneret(this);
    }
}
