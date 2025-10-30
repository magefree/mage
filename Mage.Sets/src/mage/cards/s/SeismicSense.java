package mage.cards.s;

import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.hint.common.LandsYouControlHint;
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
public final class SeismicSense extends CardImpl {

    public SeismicSense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        this.subtype.add(SubType.LESSON);

        // Look at the top X cards of your library, where X is the number of lands you control. You may reveal a creature or land card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                LandsYouControlCount.instance, 1,
                StaticFilters.FILTER_CARD_CREATURE_OR_LAND,
                PutCards.HAND, PutCards.BOTTOM_RANDOM
        ));
        this.getSpellAbility().addHint(LandsYouControlHint.instance);
    }

    private SeismicSense(final SeismicSense card) {
        super(card);
    }

    @Override
    public SeismicSense copy() {
        return new SeismicSense(this);
    }
}
