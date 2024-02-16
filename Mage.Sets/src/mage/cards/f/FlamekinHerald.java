package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CommanderPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlamekinHerald extends CardImpl {

    private static final FilterCard filter = new FilterCard("Commander spells you cast");

    static {
        filter.add(CommanderPredicate.instance);
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public FlamekinHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Commander spells you cast have cascade.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new CascadeAbility(false), filter)));
    }

    private FlamekinHerald(final FlamekinHerald card) {
        super(card);
    }

    @Override
    public FlamekinHerald copy() {
        return new FlamekinHerald(this);
    }
}
