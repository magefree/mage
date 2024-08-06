package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.card.CastFromZonePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildsearScouringMaw extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("enchantment spells you cast from your hand");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
        filter.add(new CastFromZonePredicate(Zone.HAND));
    }

    public WildsearScouringMaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Enchantment spells you cast from your hand have cascade.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledSpellsEffect(new CascadeAbility(false), filter)
        ));
    }

    private WildsearScouringMaw(final WildsearScouringMaw card) {
        super(card);
    }

    @Override
    public WildsearScouringMaw copy() {
        return new WildsearScouringMaw(this);
    }
}
