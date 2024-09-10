package mage.cards.m;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class MakindiStampede extends ModalDoubleFacedCard {

    public MakindiStampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{3}{W}{W}",
                "Makindi Mesas", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Makindi Stampede
        // Sorcery

        // Creatures you control get +2/+2 until end of turn.
        this.getLeftHalfCard().getSpellAbility().addEffect(new BoostControlledEffect(2, 2, Duration.EndOfTurn));

        // 2.
        // Makindi Mesas
        // Land

        // Makindi Mesas enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());
    }

    private MakindiStampede(final MakindiStampede card) {
        super(card);
    }

    @Override
    public MakindiStampede copy() {
        return new MakindiStampede(this);
    }
}
