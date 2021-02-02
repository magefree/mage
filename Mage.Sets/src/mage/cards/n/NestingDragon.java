package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.NestingDragonToken;

/**
 *
 * @author TheElk801
 */
public final class NestingDragon extends CardImpl {

    public NestingDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Landfall — Whenever a land enters the battlefield under your control, create a 0/2 red Dragon Egg creature token with defender and "When this creature dies, create a 2/2 red Dragon creature token with flying and '{R}: This creature gets +1/+0 until end of turn.'"
        this.addAbility(new LandfallAbility(
                new CreateTokenEffect(new NestingDragonToken()), false
        ));
    }

    private NestingDragon(final NestingDragon card) {
        super(card);
    }

    @Override
    public NestingDragon copy() {
        return new NestingDragon(this);
    }
}
