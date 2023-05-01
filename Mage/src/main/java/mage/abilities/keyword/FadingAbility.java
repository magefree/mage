package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/*
 * 702.31. Fading
 *
 * 702.31a Fading is a keyword that represents two abilities. “Fading N” means “This permanent enters the battlefield with N fade counters on it” and “At the beginning of your upkeep, remove a fade counter from this permanent. If you can't, sacrifice the permanent.”
 *
 */
public class FadingAbility extends EntersBattlefieldAbility {

    private String ruleText;

    public FadingAbility(int fadeCounter, Card card) {
        this(fadeCounter, card, false);
    }

    public FadingAbility(int fadeCounter, Card card, boolean shortRuleText) {
        super(new AddCountersSourceEffect(CounterType.FADE.createInstance(fadeCounter)), "with");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new FadingEffect(), TargetController.YOU, false);
        ability.setRuleVisible(false);
        addSubAbility(ability);
        String cardTypeName;
        if (card.getCardType().contains(CardType.CREATURE)) {
            cardTypeName = "creature";
        } else if (card.getCardType().contains(CardType.ARTIFACT)) {
            cardTypeName = "artifact";
        } else if (card.getCardType().contains(CardType.ENCHANTMENT)) {
            cardTypeName = "enchantment";
        } else {
            cardTypeName = "permanent";
        }
        ruleText = "Fading " + fadeCounter
                + (shortRuleText ? ""
                        : " <i>(This " + cardTypeName + " enters the battlefield with " + CardUtil.numberToText(fadeCounter) + " fade counters on it."
                        + " At the beginning of your upkeep, remove a fade counter from it. If you can't, sacrifice it.)</i>");
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

class FadingEffect extends OneShotEffect {

    FadingEffect() {
        super(Outcome.Sacrifice);
        staticText = "remove a fade counter from {this}. If you can't, sacrifice it";
    }

    FadingEffect(final FadingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            int amount = permanent.getCounters(game).getCount(CounterType.FADE);
            if (amount > 0) {
                permanent.removeCounters(CounterType.FADE.createInstance(), source, game);
            } else {
                permanent.sacrifice(source, game);
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
