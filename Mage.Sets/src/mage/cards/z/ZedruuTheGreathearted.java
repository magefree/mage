
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsYouOwnThatOpponentsControlCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author andyfries
 */
public final class ZedruuTheGreathearted extends CardImpl {

    public ZedruuTheGreathearted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINOTAUR, SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you gain X life and draw X cards, where X is the number of permanents you own that your opponents control.
        Effect effect = new GainLifeEffect(PermanentsYouOwnThatOpponentsControlCount.instance);
        effect.setText("you gain X life");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false);
        effect = new DrawCardSourceControllerEffect(PermanentsYouOwnThatOpponentsControlCount.instance);
        effect.setText("and draw X cards, where X is the number of permanents you own that your opponents control");
        ability.addEffect(effect);
        this.addAbility(ability);

        // {R}{W}{U}: Target opponent gains control of target permanent you control.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ZedruuTheGreatheartedEffect(), new ManaCostsImpl<>("{U}{R}{W}"));
        ability.addTarget(new TargetOpponent());
        ability.addTarget(new TargetControlledPermanent());
        this.addAbility(ability);
    }

    private ZedruuTheGreathearted(final ZedruuTheGreathearted card) {
        super(card);
    }

    @Override
    public ZedruuTheGreathearted copy() {
        return new ZedruuTheGreathearted(this);
    }

    class ZedruuTheGreatheartedEffect extends ContinuousEffectImpl {

        private MageObjectReference targetPermanentReference;

        public ZedruuTheGreatheartedEffect() {
            super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
            this.staticText = "Target opponent gains control of target permanent you control";
        }

        public ZedruuTheGreatheartedEffect(final ZedruuTheGreatheartedEffect effect) {
            super(effect);
            this.targetPermanentReference = effect.targetPermanentReference;
        }

        @Override
        public ZedruuTheGreatheartedEffect copy() {
            return new ZedruuTheGreatheartedEffect(this);
        }

        @Override
        public void init(Ability source, Game game) {
            super.init(source, game);
            targetPermanentReference = new MageObjectReference(source.getTargets().get(1).getFirstTarget(), game);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = targetPermanentReference.getPermanent(game);
            if (permanent != null) {
                return permanent.changeControllerId(source.getFirstTarget(), game, source);
            } else {
                discard();
            }
            return false;
        }
    }
}
