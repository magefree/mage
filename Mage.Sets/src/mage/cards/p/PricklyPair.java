package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MercenaryToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PricklyPair extends CardImpl {

    public PricklyPair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Prickly Pair enters the battlefield, create a 1/1 red Mercenary creature token with "{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MercenaryToken())));
    }

    private PricklyPair(final PricklyPair card) {
        super(card);
    }

    @Override
    public PricklyPair copy() {
        return new PricklyPair(this);
    }
}
