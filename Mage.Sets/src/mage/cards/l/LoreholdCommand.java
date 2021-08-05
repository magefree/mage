package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Spirit32Token;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoreholdCommand extends CardImpl {

    public LoreholdCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}{W}");

        // Choose two —
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Create a 3/2 red and white Spirit creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Spirit32Token()));

        // • Creatures you control get +1/+0 and gain indestructible and haste until end of turn.
        Mode mode = new Mode(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn
        ).setText("creatures you control get +1/+0"));
        mode.addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and gain indestructible"));
        mode.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and haste until end of turn"));
        this.getSpellAbility().addMode(mode);

        // • Lorehold Command deals 3 damage to any target. Target player gains 3 life.
        mode = new Mode(new LoreholdCommandEffect());
        mode.addTarget(new TargetAnyTarget().withChooseHint("To deal damage"));
        mode.addTarget(new TargetPlayer().withChooseHint("To gain life"));
        this.getSpellAbility().addMode(mode);

        // • Sacrifice a permanent, then draw two cards.
        mode = new Mode(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT, 1, ""));
        mode.addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));
        this.getSpellAbility().addMode(mode);
    }

    private LoreholdCommand(final LoreholdCommand card) {
        super(card);
    }

    @Override
    public LoreholdCommand copy() {
        return new LoreholdCommand(this);
    }
}

class LoreholdCommandEffect extends OneShotEffect {

    LoreholdCommandEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 3 damage to any target. Target player gains 3 life";
    }

    private LoreholdCommandEffect(final LoreholdCommandEffect effect) {
        super(effect);
    }

    @Override
    public LoreholdCommandEffect copy() {
        return new LoreholdCommandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            targetPlayer.damage(3, source.getSourceId(), source, game);
        }
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (targetPermanent != null) {
            targetPermanent.damage(3, source.getSourceId(), source, game);
        }
        Player player = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (player != null) {
            player.gainLife(3, game, source);
        }
        return true;
    }
}
