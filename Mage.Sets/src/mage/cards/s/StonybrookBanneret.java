package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.IslandwalkAbility;
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
public final class StonybrookBanneret extends CardImpl {

    private static final FilterCard filter = new FilterCard("Merfolk spells and Wizard spells");

    static {
        filter.add(Predicates.or(
                SubType.MERFOLK.getPredicate(),
                SubType.WIZARD.getPredicate()));
    }

    public StonybrookBanneret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());

        // Merfolk spells and Wizard spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private StonybrookBanneret(final StonybrookBanneret card) {
        super(card);
    }

    @Override
    public StonybrookBanneret copy() {
        return new StonybrookBanneret(this);
    }
}
