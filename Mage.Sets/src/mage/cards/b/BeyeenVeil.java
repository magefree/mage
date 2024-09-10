package mage.cards.b;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class BeyeenVeil extends ModalDoubleFacedCard {

    public BeyeenVeil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{1}{U}",
                "Beyeen Coast", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Beyeen Veil
        // Instant

        // Creatures your opponents control get -2/-0 until end of turn.
        this.getLeftHalfCard().getSpellAbility().addEffect(new BoostOpponentsEffect(-2, 0, Duration.EndOfTurn));

        // 2.
        // Beyeen Coast
        // Land

        // Beyeen Coast enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U}.
        this.getRightHalfCard().addAbility(new BlueManaAbility());
    }

    private BeyeenVeil(final BeyeenVeil card) {
        super(card);
    }

    @Override
    public BeyeenVeil copy() {
        return new BeyeenVeil(this);
    }
}
