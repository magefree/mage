
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author LevelX2
 */
public final class Sporemound extends CardImpl {

    public Sporemound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.FUNGUS);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a land enters the battlefield under your control, create a 1/1 green Saproling creature token.
        Effect effect = new CreateTokenEffect(new SaprolingToken());
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(effect, new FilterLandPermanent("a land")));
    }

    public Sporemound(final Sporemound card) {
        super(card);
    }

    @Override
    public Sporemound copy() {
        return new Sporemound(this);
    }
}
