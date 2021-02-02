package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Sporemound extends CardImpl {

    public Sporemound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.FUNGUS);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a land enters the battlefield under your control, create a 1/1 green Saproling creature token.
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new SaprolingToken())));
    }

    private Sporemound(final Sporemound card) {
        super(card);
    }

    @Override
    public Sporemound copy() {
        return new Sporemound(this);
    }
}
