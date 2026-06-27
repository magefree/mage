package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.RedwingToken;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FalconWingedWonder extends CardImpl {

    public FalconWingedWonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Avian Telepathy -- When Falcon enters, create Redwing, a legendary 1/1 blue Bird Scout creature token with flying and "Whenever Redwing attacks, surveil 1."
        this.addAbility(
            new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RedwingToken()))
                .withFlavorWord("Avian Telepathy")
        );
    }

    private FalconWingedWonder(final FalconWingedWonder card) {
        super(card);
    }

    @Override
    public FalconWingedWonder copy() {
        return new FalconWingedWonder(this);
    }
}
