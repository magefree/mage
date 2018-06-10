
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class EldraziMonument extends CardImpl {

    public EldraziMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Creatures you control get +1/+1, have flying, and are indestructible.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE, false)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, new FilterCreaturePermanent())));
        Effect effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent("Creatures you control"), false);
        effect.setText("Creatures you control are indestructible");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // At the beginning of your upkeep, sacrifice a creature. If you can't, sacrifice Eldrazi Monument.
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new EldraziMonumentEffect()));
    }

    public EldraziMonument(final EldraziMonument card) {
        super(card);
    }

    @Override
    public EldraziMonument copy() {
        return new EldraziMonument(this);
    }

}

class EldraziMonumentEffect extends OneShotEffect {

    public EldraziMonumentEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice a creature. If you can't, sacrifice {this}";
    }

    public EldraziMonumentEffect(final EldraziMonumentEffect ability) {
        super(ability);
    }

    @Override
    public EldraziMonumentEffect copy() {
        return new EldraziMonumentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetControlledPermanent target = new TargetControlledCreaturePermanent();
        Player player = game.getPlayer(source.getControllerId());
        if (target.canChoose(source.getControllerId(), game)) {
            player.choose(this.outcome, target, source.getSourceId(), game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                return permanent.sacrifice(source.getSourceId(), game);
            }
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return permanent.sacrifice(source.getSourceId(), game);
        }
        return false;

    }

}
