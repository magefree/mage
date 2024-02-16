package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllianceAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RumorGatherer extends CardImpl {

    public RumorGatherer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Alliance — Whenever another creature enters the battlefield under your control, scry 1. If this is the second time this ability has resolved this turn, draw a card instead.
        this.addAbility(new AllianceAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), new ScryEffect(1),
                RumorGathererCondition.instance, "scry 1. If this is the second time " +
                "this ability has resolved this turn, draw a card instead"
        )), new AbilityResolvedWatcher());
    }

    private RumorGatherer(final RumorGatherer card) {
        super(card);
    }

    @Override
    public RumorGatherer copy() {
        return new RumorGatherer(this);
    }
}

enum RumorGathererCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return AbilityResolvedWatcher.getResolutionCount(game, source) == 2;
    }
}
