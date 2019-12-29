
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.HornetNestInsectToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class HornetNest extends CardImpl {

    public HornetNest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Whenever Hornet Nest is dealt damage, create that many 1/1 green Insect creature tokens with flying and deathtouch.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new HornetNestDealDamageEffect(), false, false, true));
    }

    public HornetNest(final HornetNest card) {
        super(card);
    }

    @Override
    public HornetNest copy() {
        return new HornetNest(this);
    }
}

class HornetNestDealDamageEffect extends OneShotEffect {

    public HornetNestDealDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "create that many 1/1 green Insect creature tokens with flying and deathtouch";
    }

    public HornetNestDealDamageEffect(final HornetNestDealDamageEffect effect) {
        super(effect);
    }

    @Override
    public HornetNestDealDamageEffect copy() {
        return new HornetNestDealDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                return new CreateTokenEffect(new HornetNestInsectToken(), amount).apply(game, source);
            }
        }
        return false;
    }
}
