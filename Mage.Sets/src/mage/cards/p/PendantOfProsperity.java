package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class PendantOfProsperity extends CardImpl {

    public PendantOfProsperity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Pendant of Prosperity enters the battlefield under the control of an opponent of your choice.
        this.addAbility(new EntersBattlefieldAbility(new PendantOfProsperityETBEffect()));

        // {2}, {T}: Draw a card, then you may put a land card from your hand onto the battlefield. Pendant of Prosperity's owner draws a card, then that player may put a land card from their hand onto the battlefield.
        Ability ability = new SimpleActivatedAbility(new PendantOfProsperityEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private PendantOfProsperity(final PendantOfProsperity card) {
        super(card);
    }

    @Override
    public PendantOfProsperity copy() {
        return new PendantOfProsperity(this);
    }
}

class PendantOfProsperityETBEffect extends OneShotEffect {

    PendantOfProsperityETBEffect() {
        super(Benefit);
        staticText = "under the control of an opponent of your choice";
    }

    private PendantOfProsperityETBEffect(final PendantOfProsperityETBEffect effect) {
        super(effect);
    }

    @Override
    public PendantOfProsperityETBEffect copy() {
        return new PendantOfProsperityETBEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Target target = new TargetOpponent();
        target.setNotTarget(true);
        if (!controller.choose(Benefit, target, source.getSourceId(), game)) {
            return false;
        }
        Player player = game.getPlayer(target.getFirstTarget());
        if (player == null) {
            return false;
        }
        ContinuousEffect continuousEffect = new GainControlTargetEffect(
                Duration.WhileOnBattlefield, true, player.getId()
        );
        continuousEffect.setTargetPointer(new FixedTarget(
                source.getSourceId(), source.getSourceObjectZoneChangeCounter()
        ));
        game.addEffect(continuousEffect, source);
        return true;
    }

}

class PendantOfProsperityEffect extends OneShotEffect {

    private static final Effect effect1 = new DrawCardSourceControllerEffect(1);
    private static final Effect effect2 = new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A);

    PendantOfProsperityEffect() {
        super(Benefit);
        staticText = "Draw a card, then you may put a land card from your hand onto the battlefield. " +
                "{this}'s owner draws a card, then that player may put a land card from their hand onto the battlefield.";
    }

    private PendantOfProsperityEffect(final PendantOfProsperityEffect effect) {
        super(effect);
    }

    @Override
    public PendantOfProsperityEffect copy() {
        return new PendantOfProsperityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        effect1.apply(game, source);
        effect2.apply(game, source);
        Player player = game.getPlayer(game.getOwnerId(game.getPermanent(source.getSourceId())));
        if (player == null) {
            return false;
        }
        player.drawCards(1, game);
        if (!player.chooseUse(outcome, "Put a land into play from your hand?", source, game)) {
            return true;
        }
        TargetCardInHand target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_LAND_A);
        if (!player.choose(outcome, player.getHand(), target, game)) {
            return true;
        }
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        return true;
    }
}
