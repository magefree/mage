package mage.cards.s;

import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilundiVision extends CardImpl {

    public SilundiVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.s.SilundiIsle.class;

        // Look at the top six cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(
                new LookLibraryAndPickControllerEffect(
                        StaticValue.get(6), false, StaticValue.get(1),
                        StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, Zone.LIBRARY,
                        false, true, false, Zone.HAND,
                        true, false, false
                ).setBackInRandomOrder(true).setText("Look at the top six cards of your library. " +
                        "You may reveal an instant or sorcery card from among them " +
                        "and put it into your hand. Put the rest on the bottom of your library in a random order.")
        );
    }

    private SilundiVision(final SilundiVision card) {
        super(card);
    }

    @Override
    public SilundiVision copy() {
        return new SilundiVision(this);
    }
}
