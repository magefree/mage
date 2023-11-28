package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.combat.GoadAllEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.PermanentInListPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class FellBeastsShriek extends CardImpl {

    public FellBeastsShriek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{R}");

        // Each opponent chooses a creature they control. Tap and goad the chosen creatures.
        this.getSpellAbility().addEffect(new FellBeastsShriekEffect());
        // Splice onto instant or sorcery {2}{U}{R}
        this.addAbility(new SpliceAbility(SpliceAbility.INSTANT_OR_SORCERY, "{2}{U}{R}"));
    }

    private FellBeastsShriek(final FellBeastsShriek card) {
        super(card);
    }

    @Override
    public FellBeastsShriek copy() {
        return new FellBeastsShriek(this);
    }
}

class FellBeastsShriekEffect extends OneShotEffect {

    public FellBeastsShriekEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each opponent chooses a creature they control. Tap and goad the chosen creatures.";
    }

    private FellBeastsShriekEffect(final FellBeastsShriekEffect effect) {
        super(effect);
    }

    @Override
    public FellBeastsShriekEffect copy() {
        return new FellBeastsShriekEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Permanent> creaturesChosen = new ArrayList<>();

            // For each opponent, get the creature to tap+goad
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (controller.hasOpponent(playerId, game)) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
                        target.withNotTarget(true);
                        if (opponent.choose(Outcome.Detriment, target, source, game)) {
                            creaturesChosen.add(game.getPermanent(target.getTargets().get(0)));
                        }
                    }
                }
            }

            FilterPermanent filter = new FilterCreaturePermanent();
            filter.add(new PermanentInListPredicate(creaturesChosen));
            new TapAllEffect(filter).apply(game, source);
            ContinuousEffect goadEffect = new GoadAllEffect(filter);
            game.addEffect(goadEffect, source);
            return true;
        }
        return false;
    }
}
