package mage.cards.n;

import mage.MageInt;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NessianWanderer extends CardImpl {

    public NessianWanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Constellation — Whenever an enchantment enters the battelfield under your control, look at the top three cards of your library.
        // You may reveal a land card from among them and put that card into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new ConstellationAbility(
                new LookLibraryAndPickControllerEffect(3, 1, StaticFilters.FILTER_CARD_LAND_A, PutCards.HAND, PutCards.BOTTOM_RANDOM)
                .setText("look at the top three cards of your library. " +
                        "You may reveal a land card from among them and put that card into your hand. " +
                        "Put the rest on the bottom of your library in a random order"),
                false, false));
    }

    private NessianWanderer(final NessianWanderer card) {
        super(card);
    }

    @Override
    public NessianWanderer copy() {
        return new NessianWanderer(this);
    }
}
