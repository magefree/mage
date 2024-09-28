package mage.cards.s;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.Condition;
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
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.CardsLeftGraveyardWatcher;

import java.util.UUID;

/**
 * @author Alex-Vasile, Merlingilb, xenohedron
 */
public class SyrixCarrierOfTheFlame extends CardImpl {

    private static final FilterPermanent anotherPhoenixFilter = new FilterControlledPermanent(SubType.PHOENIX, "another Phoenix you control");
    private static final FilterPermanent phoenixFilter = new FilterControlledPermanent(SubType.PHOENIX, "Phoenix you control");
    static {
        anotherPhoenixFilter.add(AnotherPredicate.instance);
    }

    public SyrixCarrierOfTheFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.addSubType(SubType.PHOENIX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying, haste
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of each end step, if a creature card left your graveyard this turn,
        // target Phoenix you control deals damage equal to its power to any target.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new DamageWithPowerFromOneToAnotherTargetEffect(),
                TargetController.ANY,
                SyrixCarrierOfTheFlameCondition.instance,
                false
        );
        ability.addTarget(new TargetPermanent(phoenixFilter));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability, new CardsLeftGraveyardWatcher());

        // Whenever another Phoenix you control dies, you may cast Syrix, Carrier of the Flame from your graveyard.
        this.addAbility(new DiesCreatureTriggeredAbility(
                Zone.GRAVEYARD,
                new SyrixCarrierOfTheFlameCastEffect(),
                false, // already accounted for in effect
                anotherPhoenixFilter,
                false)
        );
    }

    private SyrixCarrierOfTheFlame(final SyrixCarrierOfTheFlame card) {
        super(card);
    }

    @Override
    public SyrixCarrierOfTheFlame copy() {
        return new SyrixCarrierOfTheFlame(this);
    }
}

// Based on Harness the Storm
class SyrixCarrierOfTheFlameCastEffect extends OneShotEffect {

    SyrixCarrierOfTheFlameCastEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may cast {this} from your graveyard";
    }

    private SyrixCarrierOfTheFlameCastEffect(final SyrixCarrierOfTheFlameCastEffect effect) {
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

    @Override
    public boolean apply(Game game, Ability source) {
        CardsLeftGraveyardWatcher watcher = game.getState().getWatcher(CardsLeftGraveyardWatcher.class);
        return watcher != null && watcher
                .getCardsThatLeftGraveyard(source.getControllerId(), game)
                .stream()
                .anyMatch(card -> card.isCreature(game));
    }

    @Override
    public String toString() {
        return "a creature card left your graveyard this turn";
    }
}
