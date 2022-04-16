package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilundiVision extends ModalDoubleFacesCard {

    public SilundiVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{2}{U}",
                "Silundi Isle", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Silundi Vision
        // Instant

        // Look at the top six cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getLeftHalfCard().getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                6, 1, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, PutCards.HAND, PutCards.BOTTOM_RANDOM));

        // 2.
        // Silundi Isle
        // Land

        // Silundi Isle enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U}.
        this.getRightHalfCard().addAbility(new BlueManaAbility());
    }

    private SilundiVision(final SilundiVision card) {
        super(card);
    }

    @Override
    public SilundiVision copy() {
        return new SilundiVision(this);
    }
}
