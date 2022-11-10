package mage.cards.t;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheFallOfKroog extends CardImpl {

    public TheFallOfKroog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Choose target opponent. Destroy target land that player controls. The Fall of Kroog deals 3 damage to that player and 1 damage to each creature they control.
        this.getSpellAbility().addEffect(new DestroyTargetEffect()
                .setText("choose target opponent. Destroy target land that player controls")
                .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addEffect(new DamageTargetEffect(
                3, true, "that player"
        ));
        this.getSpellAbility().addEffect(new DamageAllControlledTargetEffect(
                1, StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and 1 damage to each creature they control"));
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addTarget(new TheFallOfKroogTarget());
    }

    private TheFallOfKroog(final TheFallOfKroog card) {
        super(card);
    }

    @Override
    public TheFallOfKroog copy() {
        return new TheFallOfKroog(this);
    }
}

class TheFallOfKroogTarget extends TargetPermanent {

    TheFallOfKroogTarget() {
        super(1, 1, StaticFilters.FILTER_LAND, false);
    }

    private TheFallOfKroogTarget(final TheFallOfKroogTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Player player = game.getPlayer(source.getFirstTarget());
        Permanent permanent = game.getPermanent(id);
        return player != null
                && permanent != null
                && permanent.isControlledBy(player.getId())
                && super.canTarget(id, source, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Player player = Optional
                .ofNullable(game.getObject(source))
                .filter(StackObject.class::isInstance)
                .map(StackObject.class::cast)
                .map(StackObject::getStackAbility)
                .map(Ability::getFirstTarget)
                .map(game::getPlayer)
                .orElse(null);
        if (player == null) {
            return new HashSet<>();
        }
        return super
                .possibleTargets(sourceControllerId, source, game)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isControlledBy(player.getId()))
                .map(MageItem::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public TheFallOfKroogTarget copy() {
        return new TheFallOfKroogTarget(this);
    }
}
