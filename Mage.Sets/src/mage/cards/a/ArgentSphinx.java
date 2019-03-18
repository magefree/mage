
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public final class ArgentSphinx extends CardImpl {

    public ArgentSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // <i>Metalcraft</i> &mdash; {U}: Exile Argent Sphinx. Return it to the battlefield under your control at the beginning of the next end step. Activate this ability only if you control three or more artifacts.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new ArgentSphinxEffect(), new ManaCostsImpl("{U}"), MetalcraftCondition.instance);
        ability.setAbilityWord(AbilityWord.METALCRAFT);
        this.addAbility(ability);
    }

    public ArgentSphinx(final ArgentSphinx card) {
        super(card);
    }

    @Override
    public ArgentSphinx copy() {
        return new ArgentSphinx(this);
    }

}

class ArgentSphinxEffect extends OneShotEffect {

    private static final String effectText = "Exile {this}. Return it to the battlefield under your control at the beginning of the next end step";

    ArgentSphinxEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    ArgentSphinxEffect(ArgentSphinxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (permanent != null && sourceObject != null) {
            if (permanent.moveToExile(source.getSourceId(), sourceObject.getIdName(), source.getSourceId(), game)) {
                //create delayed triggered ability
                Effect effect = new ReturnToBattlefieldUnderYourControlTargetEffect();
                effect.setText("Return it to the battlefield under your control at the beginning of the next end step");
                effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public ArgentSphinxEffect copy() {
        return new ArgentSphinxEffect(this);
    }

}
