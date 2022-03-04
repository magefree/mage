
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.InsectToken;
import mage.players.Player;

/**
 *
 * @author Temba21
 */
public final class BroodhatchNantuko extends CardImpl {

    public BroodhatchNantuko(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.INSECT, SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Broodhatch Nantuko is dealt damage, you may create that many 1/1 green Insect creature tokens.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new BroodhatchNantukoDealDamageEffect(), true, false));
        // Morph {2}{G}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{2}{G}")));
    }

    private BroodhatchNantuko(final BroodhatchNantuko card) {
        super(card);
    }

    @Override
    public BroodhatchNantuko copy() {
        return new BroodhatchNantuko(this);
    }
}

class BroodhatchNantukoDealDamageEffect extends OneShotEffect {

    public BroodhatchNantukoDealDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "create that many 1/1 green Insect creature tokens";
    }

    public BroodhatchNantukoDealDamageEffect(final BroodhatchNantukoDealDamageEffect effect) {
        super(effect);
    }

    @Override
    public BroodhatchNantukoDealDamageEffect copy() {
        return new BroodhatchNantukoDealDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                return new CreateTokenEffect(new InsectToken(), amount).apply(game, source);
            }
        }
        return false;
    }
}
