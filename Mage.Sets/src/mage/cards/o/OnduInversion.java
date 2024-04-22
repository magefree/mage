package mage.cards.o;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class OnduInversion extends ModalDoubleFacedCard {

    public OnduInversion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{6}{W}{W}",
                "Ondu Skyruins", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Ondu Inversion
        // Sorcery

        // Destroy all nonland permanents.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENTS_NON_LAND));

        // 2.
        // Ondu Skyruins
        // Land

        // Ondu Skyruins enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());
    }

    private OnduInversion(final OnduInversion card) {
        super(card);
    }

    @Override
    public OnduInversion copy() {
        return new OnduInversion(this);
    }
}
