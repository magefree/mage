package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterOpponent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.Objects;
import java.util.UUID;

/**
 * @author jeffwadsworth, Susucr
 */
public class KeeperOfTheMind extends CardImpl {

    public KeeperOfTheMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {U}, {tap}: Choose target opponent who had at least two more cards in hand than you did as you activated this ability. Draw a card.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DrawCardSourceControllerEffect(1)
                        .setText("choose target opponent who has at least two more cards " +
                                "in hand than you do as you activate this ability. Draw a card"),
                new ManaCostsImpl<>("{U}"), KeeperOfTheMindCondition.instance
        ).hideCondition();
        ability.addCost(new TapSourceCost());
        ability.setTargetAdjuster(KeeperOfTheMindAdjuster.instance);
        this.addAbility(ability);
    }

    private KeeperOfTheMind(final KeeperOfTheMind card) {
        super(card);
    }

    @Override
    public KeeperOfTheMind copy() {
        return new KeeperOfTheMind(this);
    }
}

enum KeeperOfTheMindAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return;
        }
        ability.getTargets().clear();
        FilterOpponent filter = new FilterOpponent("opponent with two or more card in hand than you did as you activated this ability");
        filter.add(new KeeperOfTheMindPredicate(controller.getHand().size()));
        TargetPlayer target = new TargetPlayer(1, 1, false, filter);
        target.setTargetController(controller.getId());
        ability.addTarget(target);
    }
}

class KeeperOfTheMindPredicate implements ObjectSourcePlayerPredicate<Player> {

    private final int controllerHandSize;

    KeeperOfTheMindPredicate(int controllerHandSize) {
        this.controllerHandSize = controllerHandSize;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        if (targetPlayer == null) {
            return false;
        }
        int countHandTargetPlayer = targetPlayer.getHand().size();

        return countHandTargetPlayer - 2 >= controllerHandSize;
    }

    @Override
    public String toString() {
        return "opponent who had at least two more cards in hand than you did as you activated this ability";
    }
}

// Is there an opponent with 2 cards in hand for even try activation?
enum KeeperOfTheMindCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int handSize = controller.getHand().size();
        return game.getOpponents(controller.getId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .anyMatch(player -> player.getHand().size() - 2 >= handSize);
    }
}
