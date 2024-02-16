package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class KazanduMammoth extends ModalDoubleFacedCard {

    public KazanduMammoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEPHANT}, "{1}{G}{G}",
                "Kazandu Valley", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Kazandu Mammoth
        // Creature — Elephant
        this.getLeftHalfCard().setPT(new MageInt(3), new MageInt(3));

        // Landfall — Whenever a land enters the battlefield under your control, Kazandu Mammoth gets +2/+2 until end of turn.
        this.getLeftHalfCard().addAbility(new LandfallAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn)));

        // 2.
        // Kazandu Valley
        // Land

        // Kazandu Valley enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());
    }

    private KazanduMammoth(final KazanduMammoth card) {
        super(card);
    }

    @Override
    public KazanduMammoth copy() {
        return new KazanduMammoth(this);
    }
}
