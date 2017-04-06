package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Stravant
 */
public class RagsRiches extends SplitCard {
    public RagsRiches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}","{5}{U}{U}",false);

        // Rags
        // All creatures get -2/-2 until end of turn.
        getLeftHalfCard().getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn));

        // to

        // Riches
        // Each opponent chooses a creature he or she controls. You gain control of each of those creatures.
        ((CardImpl) (getRightHalfCard())).addAbility(new AftermathAbility());
        getRightHalfCard().getSpellAbility().addEffect(new RichesEffect());
    }

    public RagsRiches(final RagsRiches card) {
        super(card);
    }

    @Override
    public RagsRiches copy() {
        return new RagsRiches(this);
    }
}

class RichesEffect extends OneShotEffect {

    public RichesEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each opponent chooses a creature he or she controls. You gain control of each of those creatures.";
    }

    public RichesEffect(final RichesEffect effect) {
        super(effect);
    }

    @Override
    public RichesEffect copy() {
        return new RichesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Cards creaturesToSteal = new CardsImpl();

            // For each opponent, get the creature to steal
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (controller.hasOpponent(playerId, game)) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
                        if (opponent.chooseTarget(Outcome.Detriment, target, source, game)) {
                            creaturesToSteal.add(target.getTargets().get(0));
                        }
                    }
                }
            }

            // Has to be done as a separate loop in case there's a situation where one creature's
            // controller depends on another creatures controller.
            for (UUID target: creaturesToSteal) {
                GainControlTargetEffect eff = new GainControlTargetEffect(Duration.EndOfGame, true);
                eff.setTargetPointer(new FixedTarget(target));
                game.addEffect(eff, source);
            }

            return true;
        }
        return false;
    }
}