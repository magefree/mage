package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class StarkeOfRath extends CardImpl {

    public StarkeOfRath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Destroy target artifact or creature. That permanent's controller gains control of Starke of Rath.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StarkeOfRathEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);

    }

    private StarkeOfRath(final StarkeOfRath card) {
        super(card);
    }

    @Override
    public StarkeOfRath copy() {
        return new StarkeOfRath(this);
    }
}

class StarkeOfRathEffect extends OneShotEffect {

    StarkeOfRathEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target artifact or creature. That permanent's controller gains control of {this}";
    }

    private StarkeOfRathEffect(final StarkeOfRathEffect effect) {
        super(effect);
    }

    @Override
    public StarkeOfRathEffect copy() {
        return new StarkeOfRathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetPermanent != null) {
                targetPermanent.destroy(source, game, false);
                ContinuousEffect effect = new StarkeOfRathControlEffect();
                effect.setTargetPointer(new FixedTarget(targetPermanent.getControllerId()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class StarkeOfRathControlEffect extends ContinuousEffectImpl {

    StarkeOfRathControlEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "That permanent's controller gains control of {this}";
    }

    private StarkeOfRathControlEffect(final StarkeOfRathControlEffect effect) {
        super(effect);
    }

    @Override
    public StarkeOfRathControlEffect copy() {
        return new StarkeOfRathControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player newController = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (permanent != null && newController != null) {
            return permanent.changeControllerId(newController.getId(), game, source);
        } else {
            discard();
        }
        return false;
    }

}
