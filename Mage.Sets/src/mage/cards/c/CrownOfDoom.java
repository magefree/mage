
package mage.cards.c;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class CrownOfDoom extends CardImpl {

    private static final FilterPlayer filter = new FilterPlayer("player other than {this}'s owner");

    static {
        filter.add(new CrownOfDoomPredicate());
    }

    public CrownOfDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a creature attacks you or a planeswalker you control, it gets +2/+0 until end of turn.
        Effect effect = new BoostTargetEffect(2, 0, Duration.EndOfTurn);
        effect.setText("it gets +2/+0 until end of turn");
        this.addAbility(new AttacksAllTriggeredAbility(effect, false, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.PERMANENT, true));

        //TODO: Make ability properly copiable
        // {2}: Target player other than Crown of Doom's owner gains control of it. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new CrownOfDoomEffect(), new ManaCostsImpl("{2}"), MyTurnCondition.instance);
        ability.addTarget(new TargetPlayer(1, 1, false, filter));
        this.addAbility(ability);
    }

    public CrownOfDoom(final CrownOfDoom card) {
        super(card);
    }

    @Override
    public CrownOfDoom copy() {
        return new CrownOfDoom(this);
    }
}

class CrownOfDoomPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Player>> {

    public CrownOfDoomPredicate() {
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(input.getSourceId());
        if (targetPlayer == null || sourceObject == null) {
            return false;
        }
        return !targetPlayer.getId().equals(sourceObject.getOwnerId());
    }

    @Override
    public String toString() {
        return "Owner(" + ')';
    }
}

class CrownOfDoomEffect extends OneShotEffect {

    public CrownOfDoomEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target player other than {this}'s owner gains control of it";
    }

    public CrownOfDoomEffect(final CrownOfDoomEffect effect) {
        super(effect);
    }

    @Override
    public CrownOfDoomEffect copy() {
        return new CrownOfDoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player newController = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && newController != null && !Objects.equals(controller.getId(), newController.getId())) {
            // Duration.Custom = effect ends if Artifact leaves the current zone (battlefield)
            ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, newController.getId());
            effect.setTargetPointer(new FixedTarget(source.getSourceId()));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
