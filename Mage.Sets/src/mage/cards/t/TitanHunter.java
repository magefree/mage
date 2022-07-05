package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TitanHunter extends CardImpl {

    private static final Condition condition = new InvertCondition(MorbidCondition.instance);

    public TitanHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // At the beginning of each player's end step, if no creatures died this turn, Titan Hunter deals 4 damage to that player.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new TitanHunterEffect(), TargetController.EACH_PLAYER, false
                ), condition, "At the beginning of each player's end step, " +
                "if no creatures died this turn, {this} deals 4 damage to that player."
        ).addHint(MorbidHint.instance));

        // {1}{B}, Sacrifice a creature: You gain 4 life.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(4), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
    }

    private TitanHunter(final TitanHunter card) {
        super(card);
    }

    @Override
    public TitanHunter copy() {
        return new TitanHunter(this);
    }
}

class TitanHunterEffect extends OneShotEffect {

    TitanHunterEffect() {
        super(Outcome.Benefit);
    }

    private TitanHunterEffect(final TitanHunterEffect effect) {
        super(effect);
    }

    @Override
    public TitanHunterEffect copy() {
        return new TitanHunterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.damagePlayerOrPlaneswalker(
                game.getActivePlayerId(), 4, source.getSourceId(),
                source, game, false, true
        ) > 0;
    }
}