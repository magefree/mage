
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.AngelToken;
import mage.game.permanent.token.SoldierToken;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class DecreeOfJustice extends CardImpl {

    public DecreeOfJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{2}{W}{W}");

        // Create X 4/4 white Angel creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new AngelToken(), new ManacostVariableValue()));

        // Cycling {2}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{W}")));

        // When you cycle Decree of Justice, you may pay {X}. If you do, create X 1/1 white Soldier creature tokens.
        Ability ability = new CycleTriggeredAbility(new DecreeOfJusticeCycleEffect(), true);
        this.addAbility(ability);
    }

    public DecreeOfJustice(final DecreeOfJustice card) {
        super(card);
    }

    @Override
    public DecreeOfJustice copy() {
        return new DecreeOfJustice(this);
    }
}

class DecreeOfJusticeCycleEffect extends OneShotEffect {

    DecreeOfJusticeCycleEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may pay {X}. If you do, create X 1/1 white Soldier creature tokens";
    }

    DecreeOfJusticeCycleEffect(final DecreeOfJusticeCycleEffect effect) {
        super(effect);
    }

    @Override
    public DecreeOfJusticeCycleEffect copy() {
        return new DecreeOfJusticeCycleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int X = player.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
            Cost cost = new GenericManaCost(X);
            if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)) {
                Token token = new SoldierToken();
                token.putOntoBattlefield(X, game, source.getSourceId(), source.getControllerId());
                return true;
            }
        }
        return false;
    }
}
