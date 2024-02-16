package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DiscerningFinancier extends CardImpl {
    private static final Condition condition = new OpponentControlsMoreCondition(StaticFilters.FILTER_LAND);

    private static final Hint hint = new ConditionHint(
            condition, "An opponent controls more land than you", null,
            "No opponent controls more land than you", null, true
    );

    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent(SubType.TREASURE, "Treasure you control");

    public DiscerningFinancier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, if an opponent controls more lands than you, create a Treasure token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new CreateTokenEffect(new TreasureToken()),
                        TargetController.YOU, false
                ),
                condition,
                "At the beginning of your upkeep, if an opponent controls more lands than you, create a Treasure token."
        ).addHint(hint));


        // {2}{W}: Choose another player. That player gains control of target Treasure you control. You draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DiscerningFinancierEffect(),
                new ManaCostsImpl("{2}{W}")
        );
        ability.addTarget(new TargetControlledPermanent(filter));
        ability.addEffect(new DrawCardSourceControllerEffect(1, "you"));
        this.addAbility(ability);
    }

    private DiscerningFinancier(final DiscerningFinancier card) {
        super(card);
    }

    @Override
    public DiscerningFinancier copy() {
        return new DiscerningFinancier(this);
    }
}

class DiscerningFinancierEffect extends OneShotEffect {

    private static final FilterPlayer filter = new FilterPlayer("another player");

    static {
        filter.add(TargetController.NOT_YOU.getPlayerPredicate());
    }

    DiscerningFinancierEffect() {
        super(Outcome.Detriment);
        staticText = "choose another player. That player gains control of target Treasure you control";
    }

    private DiscerningFinancierEffect(final DiscerningFinancierEffect effect) {
        super(effect);
    }

    @Override
    public DiscerningFinancierEffect copy() {
        return new DiscerningFinancierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetPlayer target = new TargetPlayer(filter);
        target.withNotTarget(true);
        Permanent treasure = game.getPermanent(source.getFirstTarget());
        if (treasure == null || !target.canChoose(source.getControllerId(), source, game)) {
            return false;
        }

        target.choose(Outcome.Benefit, source.getControllerId(), source.getSourceId(), source, game);
        Player chosen = game.getPlayer(target.getFirstTarget());
        if (chosen == null) {
            return false;
        }

        game.addEffect(new GainControlTargetEffect(
                Duration.WhileOnBattlefield, true, chosen.getId()
        ).setTargetPointer(new FixedTarget(source.getFirstTarget(), game)), source);
        return true;
    }

}