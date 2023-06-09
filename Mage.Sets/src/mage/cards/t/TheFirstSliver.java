package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheFirstSliver extends CardImpl {

    private static final FilterCard filter = new FilterCard("Sliver spells you cast");

    static {
        filter.add(SubType.SLIVER.getPredicate());
    }

    public TheFirstSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Cascade
        this.addAbility(new CascadeAbility(false));

        // Sliver spells you cast have cascade.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new CascadeAbility(false), filter)));
    }

    private TheFirstSliver(final TheFirstSliver card) {
        super(card);
    }

    @Override
    public TheFirstSliver copy() {
        return new TheFirstSliver(this);
    }
}
