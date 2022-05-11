package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChanneledForce extends CardImpl {

    public ChanneledForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{R}");

        // As an additional cost to cast this spell, discard X cards.
        this.getSpellAbility().addCost(new DiscardXTargetCost(StaticFilters.FILTER_CARD_CARDS));

        // Target player draws X cards. Channeled Force deals X damage to up to one target creature or planeswalker.
        this.getSpellAbility().addEffect(new ChanneledForceEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private ChanneledForce(final ChanneledForce card) {
        super(card);
    }

    @Override
    public ChanneledForce copy() {
        return new ChanneledForce(this);
    }
}

class ChanneledForceEffect extends OneShotEffect {

    ChanneledForceEffect() {
        super(Outcome.Benefit);
        staticText = "Target player draws X cards. {this} deals X damage to up to one target creature or planeswalker.";
    }

    private ChanneledForceEffect(final ChanneledForceEffect effect) {
        super(effect);
    }

    @Override
    public ChanneledForceEffect copy() {
        return new ChanneledForceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = GetXValue.instance.calculate(game, source, this);
        if (xValue == 0) {
            return false;
        }
        Player player = game.getPlayer(source.getTargets().get(0).getFirstTarget());
        if (player != null) {
            player.drawCards(xValue, source, game);
        }
        game.damagePlayerOrPlaneswalker(
                source.getTargets().get(1).getFirstTarget(), xValue,
                source.getSourceId(), source, game, false, true
        );
        return true;
    }
}
