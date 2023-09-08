package mage.cards.a;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AngelsTrumpet extends CardImpl {

    public AngelsTrumpet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // All creatures have vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, new FilterCreaturePermanent())));

        // At the beginning of each player's end step, tap all untapped creatures that player controls that didn't attack this turn. Angel's Trumpet deals damage to the player equal to the number of creatures tapped this way.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AngelsTrumpetTapEffect(), TargetController.ANY, false), new AttackedThisTurnWatcher());
    }

    private AngelsTrumpet(final AngelsTrumpet card) {
        super(card);
    }

    @Override
    public AngelsTrumpet copy() {
        return new AngelsTrumpet(this);
    }
}

class AngelsTrumpetTapEffect extends OneShotEffect {

    AngelsTrumpetTapEffect() {
        super(Outcome.Tap);
        this.staticText = "tap all untapped creatures that player controls that didn't attack this turn. Angel's Trumpet deals damage to the player equal to the number of creatures tapped this way";
    }

    private AngelsTrumpetTapEffect(final AngelsTrumpetTapEffect effect) {
        super(effect);
    }

    @Override
    public AngelsTrumpetTapEffect copy() {
        return new AngelsTrumpetTapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            int count = 0;
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game)) {
                // Untapped creatures are safe.
                if (creature.isTapped()) {
                    continue;
                }
                // Creatures that attacked are safe.
                AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
                if (watcher != null && watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(creature, game))) {
                    continue;
                }
                // Tap the rest.
                creature.tap(source, game);
                count++;
            }
            if (count > 0) {
                player.damage(count, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }
}
