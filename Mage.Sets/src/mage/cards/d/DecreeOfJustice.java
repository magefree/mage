package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
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
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class DecreeOfJustice extends CardImpl {

    public DecreeOfJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{2}{W}{W}");

        // Create X 4/4 white Angel creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new AngelToken(), ManacostVariableValue.REGULAR));

        // Cycling {2}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{W}")));

        // When you cycle Decree of Justice, you may pay {X}. If you do, create X 1/1 white Soldier creature tokens.
        Ability ability = new CycleTriggeredAbility(new DecreeOfJusticeCycleEffect());
        this.addAbility(ability);
    }

    private DecreeOfJustice(final DecreeOfJustice card) {
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

    private DecreeOfJusticeCycleEffect(final DecreeOfJusticeCycleEffect effect) {
        super(effect);
    }

    @Override
    public DecreeOfJusticeCycleEffect copy() {
        return new DecreeOfJusticeCycleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(Outcome.Benefit, "Pay {X} to create X tokens?", source, game)) {
            return false;
        }
        int payCount = ManaUtil.playerPaysXGenericMana(true, "Decree of Justice", player, source, game);
        if (payCount > 0) {
            return new SoldierToken().putOntoBattlefield(payCount, game, source, source.getControllerId());
        }
        return false;
    }
}
