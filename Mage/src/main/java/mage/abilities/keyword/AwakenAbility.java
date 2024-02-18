package mage.abilities.keyword;

import mage.MageInt;
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

import java.util.UUID;

/**
 * @author LevelX2
 */
public class AwakenAbility extends SpellAbility {

    private final String rule;
    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent(AwakenEffect.filterMessage);

    public AwakenAbility(Card card, int awakenValue, String awakenCosts) {
        super(card.getSpellAbility());
        this.newId();
        this.setCardName(card.getName() + " with awaken");
        zone = Zone.HAND;
        spellAbilityType = SpellAbilityType.BASE_ALTERNATE;

        this.clearManaCosts();
        this.clearManaCostsToPay();
        this.addCost(new ManaCostsImpl<>(awakenCosts));

        this.addTarget(new TargetControlledPermanent(filter));
        this.addEffect(new AwakenEffect(awakenValue));

        rule = "Awaken " + awakenValue + "&mdash;" + awakenCosts
                + " <i>(If you cast this spell for " + awakenCosts + ", also put "
                + CardUtil.getOneOneCountersText(awakenValue)
                + " on target land you control and it becomes a 0/0 Elemental creature with haste. It's still a land.)</i>";
    }

    protected AwakenAbility(final AwakenAbility ability) {
        super(ability);
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

}

class AwakenEffect extends OneShotEffect {

    static final String filterMessage = "a land you control to awake";

    private final int awakenValue;

    AwakenEffect(int awakenValue) {
        super(Outcome.BoostCreature);
        this.awakenValue = awakenValue;
        this.staticText = "put " + CardUtil.getOneOneCountersText(awakenValue) + " on target land you control and it becomes a 0/0 Elemental creature with haste";
    }

    private AwakenEffect(final AwakenEffect effect) {
        super(effect);
        this.awakenValue = effect.awakenValue;
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
                FixedTarget blueprintTarget = new FixedTarget(targetId, game);
                ContinuousEffect continuousEffect = new BecomesCreatureTargetEffect(new AwakenElementalToken(), false, true, Duration.Custom);
                continuousEffect.setTargetPointer(blueprintTarget.copy());
                game.addEffect(continuousEffect, source);
                Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(awakenValue));
                effect.setTargetPointer(blueprintTarget.copy());
                effect.apply(game, source);
            }
            return true;
        }
        return false;
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

    private AwakenElementalToken(final AwakenElementalToken token) {
        super(token);
    }

    public AwakenElementalToken copy() {
        return new AwakenElementalToken(this);
    }
}
