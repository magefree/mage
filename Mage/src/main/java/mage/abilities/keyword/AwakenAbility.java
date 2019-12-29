package mage.abilities.keyword;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class AwakenAbility extends SpellAbility {

    private static final Logger logger = Logger.getLogger(AwakenAbility.class);

    static private String filterMessage = "a land you control to awake";

    private String rule;
    private int awakenValue;

    public AwakenAbility(Card card, int awakenValue, String awakenCosts) {
        super(card.getSpellAbility());
        this.newId();
        this.setCardName(card.getName() + " with awaken");
        zone = Zone.HAND;
        spellAbilityType = SpellAbilityType.BASE_ALTERNATE;

        this.getManaCosts().clear();
        this.getManaCostsToPay().clear();
        this.addManaCost(new ManaCostsImpl<>(awakenCosts));

        this.addTarget(new TargetControlledPermanent(new FilterControlledLandPermanent(filterMessage)));
        this.addEffect(new AwakenEffect());
        this.awakenValue = awakenValue;
        rule = "Awaken " + awakenValue + "&mdash;" + awakenCosts
                + " <i>(If you cast this spell for " + awakenCosts + ", also put "
                + CardUtil.numberToText(awakenValue, "a")
                + " +1/+1 counters on target land you control and it becomes a 0/0 Elemental creature with haste. It's still a land.)</i>";
    }

    public AwakenAbility(final AwakenAbility ability) {
        super(ability);
        this.awakenValue = ability.awakenValue;
        this.rule = ability.rule;
    }

    @Override
    public AwakenAbility copy() {
        return new AwakenAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return rule;
    }

    class AwakenEffect extends OneShotEffect {

        private AwakenEffect() {
            super(Outcome.BoostCreature);
            this.staticText = "put " + CardUtil.numberToText(awakenValue, "a") + " +1/+1 counters on target land you control";
        }

        public AwakenEffect(final AwakenEffect effect) {
            super(effect);
        }

        @Override
        public AwakenEffect copy() {
            return new AwakenEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            UUID targetId = null;
            if (source != null && source.getTargets() != null) {
                for (Target target : source.getTargets()) {
                    if (target.getFilter() != null && target.getFilter().getMessage().equals(filterMessage)) {
                        targetId = target.getFirstTarget();
                    }
                }
                if (targetId != null) {
                    FixedTarget fixedTarget = new FixedTarget(targetId);
                    ContinuousEffect continuousEffect = new BecomesCreatureTargetEffect(new AwakenElementalToken(), false, true, Duration.Custom);
                    continuousEffect.setTargetPointer(fixedTarget);
                    game.addEffect(continuousEffect, source);
                    Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(awakenValue));
                    effect.setTargetPointer(fixedTarget);
                    return effect.apply(game, source);
                }
            } else // source should never be null, but we are seeing a lot of NPEs from this section
                if (source == null) {
                    logger.fatal("Source was null in AwakenAbility: Create a bug report or fix the source code");
                } else if (source.getTargets() == null) {
                    MageObject sourceObj = source.getSourceObject(game);
                    if (sourceObj != null) {
                        Class<? extends MageObject> sourceClass = sourceObj.getClass();
                        if (sourceClass != null) {
                            logger.fatal("getTargets was null in AwakenAbility for " + sourceClass.toString() + " : Create a bug report or fix the source code");
                        }
                    }
                }
            return true;
        }
    }

}

class AwakenElementalToken extends TokenImpl {

    public AwakenElementalToken() {
        super("", "0/0 Elemental creature with haste");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(HasteAbility.getInstance());
    }

    public AwakenElementalToken(final AwakenElementalToken token) {
        super(token);
    }

    public AwakenElementalToken copy() {
        return new AwakenElementalToken(this);
    }
}
