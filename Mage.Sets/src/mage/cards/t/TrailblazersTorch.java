package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.BlockingOrBlockedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrailblazersTorch extends CardImpl {

    public TrailblazersTorch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Trailblazer's Torch enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));

        // Whenever equipped creature becomes blocked, it deals 2 damage to each creature blocking it.
        this.addAbility(new BecomesBlockedAttachedTriggeredAbility(
                new TrailblazersTorchEffect(), false
        ).setTriggerPhrase("Whenever equipped creature becomes blocked, "));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private TrailblazersTorch(final TrailblazersTorch card) {
        super(card);
    }

    @Override
    public TrailblazersTorch copy() {
        return new TrailblazersTorch(this);
    }
}

class TrailblazersTorchEffect extends OneShotEffect {

    TrailblazersTorchEffect() {
        super(Outcome.Benefit);
        staticText = "it deals 2 damage to each creature blocking it";
    }

    private TrailblazersTorchEffect(final TrailblazersTorchEffect effect) {
        super(effect);
    }

    @Override
    public TrailblazersTorchEffect copy() {
        return new TrailblazersTorchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attacker = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (attacker == null) {
            return false;
        }
        for (Permanent blocker : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        )) {
            if (BlockingOrBlockedWatcher.check(attacker, blocker, game)) {
                blocker.damage(2, attacker.getId(), source, game);
            }
        }
        return true;
    }
}
