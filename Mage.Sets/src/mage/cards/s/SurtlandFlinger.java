package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurtlandFlinger extends CardImpl {

    public SurtlandFlinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Whenever Surtland Flinger attacks, you may sacrifice another creature. When you do, Surtland Flinger deals damage equal to the sacrificed creature's power to any target. If the sacrificed creature was a Giant, Surtland Flinger deals twice that much damage instead.
        this.addAbility(new AttacksTriggeredAbility(new SurtlandFlingerEffect(), true));
    }

    private SurtlandFlinger(final SurtlandFlinger card) {
        super(card);
    }

    @Override
    public SurtlandFlinger copy() {
        return new SurtlandFlinger(this);
    }
}

class SurtlandFlingerEffect extends OneShotEffect {

    private static final String rule
            = "{this} deals damage equal to the sacrificed creature's power to any target. " +
            "If the sacrificed creature was a Giant, {this} deals twice that much damage instead.";

    SurtlandFlingerEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice another creature. When you do, " + rule;
    }

    private SurtlandFlingerEffect(final SurtlandFlingerEffect effect) {
        super(effect);
    }

    @Override
    public SurtlandFlingerEffect copy() {
        return new SurtlandFlingerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true
        );
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        if (permanent.hasSubtype(SubType.GIANT, game)) {
            power *= 2;
        }
        power = Math.max(power, 0);
        if (!permanent.sacrifice(source, game)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(power), false, rule
        );
        ability.addTarget(new TargetAnyTarget());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
