package mage.cards.r;

import java.util.UUID;
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
import mage.constants.SpellAbilityType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Stravant
 */
public final class RagsRiches extends SplitCard {

    public RagsRiches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{2}{B}{B}", "{5}{U}{U}", SpellAbilityType.SPLIT_AFTERMATH);

        // Rags
        // All creatures get -2/-2 until end of turn.
        getLeftHalfCard().getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn));

        // to
        // Riches
        // Each opponent chooses a creature they control. You gain control of each of those creatures.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addEffect(new RichesEffect());
    }

    private RagsRiches(final RagsRiches card) {
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
        this.staticText = "Each opponent chooses a creature they control. You gain control of each of those creatures.";
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
                        target.setNotTarget(true);
                        if (opponent.choose(Outcome.Detriment, target, source, game)) {
                            creaturesToSteal.add(target.getTargets().get(0));
                        }
                    }
                }
            }

            // Has to be done as a separate loop in case there's a situation where one creature's
            // controller depends on another creatures controller.
            for (UUID target : creaturesToSteal) {
                GainControlTargetEffect eff = new GainControlTargetEffect(Duration.EndOfGame, true);
                eff.setTargetPointer(new FixedTarget(target, game));
                game.addEffect(eff, source);
            }

            return true;
        }
        return false;
    }
}
