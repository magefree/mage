package mage.cards.s;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceInGraveyardCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;
import mage.watchers.Watcher;
import mage.watchers.common.CardsLeftGraveyardWatcher;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class SyrixCarrierOfTheFlame extends CardImpl {

    private static final String description = "Phoenix you control";
    private static final FilterPermanent anotherPhoenixFilter = new FilterControlledPermanent("another Phoenix you control");
    private static final FilterPermanent phoenixFilter = new FilterControlledPermanent(description);
    static {
        anotherPhoenixFilter.add(AnotherPredicate.instance);
        anotherPhoenixFilter.add(SubType.PHOENIX.getPredicate());
        phoenixFilter.add(SubType.PHOENIX.getPredicate());
    }

    public SyrixCarrierOfTheFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.PHOENIX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying, haste
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of each end step, if a creature card left your graveyard this turn,
        // target Phoenix you control deals damage equal to its power to any target.
        BeginningOfEndStepTriggeredAbility ability = new BeginningOfEndStepTriggeredAbility(
                new DamageWithPowerFromOneToAnotherTargetEffect(),
                TargetController.ANY,
                SyrixCarrierOfTheFlameCondition.instance,
                false
        );
        ability.addTarget(new TargetPermanent(phoenixFilter));
        ability.addTarget(new TargetAnyTarget());
        ability.addWatcher(new CardsLeftGraveyardWatcher());
        this.addAbility(ability);

        // Whenever another Phoenix you control dies, you may cast Syrix, Carrier of the Flame from your graveyard.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new DiesCreatureTriggeredAbility(
                Zone.ALL,
                new SyrixCarrierOfTheFlameCastEffect(),
                false,
                anotherPhoenixFilter,
                false
        ), SourceInGraveyardCondition.instance, ""));
    }

    private SyrixCarrierOfTheFlame(final SyrixCarrierOfTheFlame card) {
        super(card);
    }

    @Override
    public SyrixCarrierOfTheFlame copy() {
        return new SyrixCarrierOfTheFlame(this);
    }
}

/**
 * Based on Harness the Storm
 */
class SyrixCarrierOfTheFlameCastEffect extends OneShotEffect {
    SyrixCarrierOfTheFlameCastEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may cast {this} from your graveyard";
    }

    SyrixCarrierOfTheFlameCastEffect(final SyrixCarrierOfTheFlameCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }
        if (controller.chooseUse(Outcome.Benefit, "Cast " + card.getIdName() + " from your graveyard?", source, game)) {
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            controller.cast(controller.chooseAbilityForCast(card, game, false),
                    game, false, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        }
        return true;
    }

    @Override
    public SyrixCarrierOfTheFlameCastEffect copy() {
        return new SyrixCarrierOfTheFlameCastEffect(this);
    }
}

/**
 * Creature card left your graveyard this turn
 */
enum SyrixCarrierOfTheFlameCondition implements Condition {
    instance;

    private static final String string = "a creature card left your graveyard this turn";

    @Override
    public boolean apply(Game game, Ability source) {
        CardsLeftGraveyardWatcher cardsLeftGraveyardWatcher = game.getState().getWatcher(CardsLeftGraveyardWatcher.class);
        return cardsLeftGraveyardWatcher != null &&
                cardsLeftGraveyardWatcher
                        .getCardsThatLeftGraveyard(source.getControllerId(), game)
                        .stream()
                        .anyMatch(card -> card.isCreature(game));
    }

    @Override
    public String toString() {
        return string;
    }
}
