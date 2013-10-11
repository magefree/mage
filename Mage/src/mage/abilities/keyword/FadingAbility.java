package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/*
 * 702.31. Fading
 *
 * 702.31a Fading is a keyword that represents two abilities. “Fading N” means “This permanent enters the battlefield with N fade counters on it” and “At the beginning of your upkeep, remove a fade counter from this permanent. If you can’t, sacrifice the permanent.”
 *
 */

public class FadingAbility extends EntersBattlefieldAbility {
    
    
    private String ruleText;
    
    public FadingAbility(int fadeCounter, Card card) {
        super(new AddCountersSourceEffect(CounterType.FADE.createInstance(fadeCounter)), "with");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new FadingEffect(), TargetController.YOU, false);
        ability.setRuleVisible(false);
        card.addAbility(ability);
        StringBuilder sb = new StringBuilder("Fading ");
        sb.append(fadeCounter);
        sb.append(" <i>(This permanent enters the battlefield with ")
        .append(fadeCounter)
        .append(" fade counters on it. ")
        .append(" At the beginning of your upkeep, remove a fade counter from this permanent. If you can’t, sacrifice the permanent.")
        .append(")</i>");
        ruleText = sb.toString();
    }

    public FadingAbility(final FadingAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;
    }

    @Override
    public EntersBattlefieldAbility copy() {
        return new FadingAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText;
    }
}
class FadingEffect extends OneShotEffect<FadingEffect> {
    FadingEffect() {
        super(Outcome.Sacrifice);
        staticText = "remove a fade counter from this permanent. If you can’t, sacrifice the permanent";
    }

    FadingEffect(final FadingEffect effect) {
        super(effect);
    }


    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            int amount = p.getCounters().getCount(CounterType.FADE);
            if (amount > 0) {
                p.removeCounters(CounterType.FADE.createInstance(), game);
            }
            else
            {
                p.sacrifice(source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public FadingEffect copy() {
        return new FadingEffect(this);
    }
}