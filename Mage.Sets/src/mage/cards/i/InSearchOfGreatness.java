package mage.cards.i;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author weirddan455
 */
public final class InSearchOfGreatness extends CardImpl {

    public InSearchOfGreatness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{G}");

        // At the beginning of your upkeep, you may cast a permanent spell from your hand with converted mana cost
        // equal to 1 plus the highest converted mana cost among other permanents you control
        // without paying its mana cost. If you don't, scry 1.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new InSearchOfGreatnessEffect(), TargetController.YOU, false
        ));
    }

    private InSearchOfGreatness(final InSearchOfGreatness card) {
        super(card);
    }

    @Override
    public InSearchOfGreatness copy() {
        return new InSearchOfGreatness(this);
    }
}

class InSearchOfGreatnessEffect extends OneShotEffect {

    public InSearchOfGreatnessEffect() {
        super(Outcome.PlayForFree);
        staticText = "you may cast a permanent spell from your hand with mana value "
                + "equal to 1 plus the highest mana value among other permanents you control "
                + "without paying its mana cost. If you don't, scry 1";
    }

    private InSearchOfGreatnessEffect(final InSearchOfGreatnessEffect effect) {
        super(effect);
    }

    @Override
    public InSearchOfGreatnessEffect copy() {
        return new InSearchOfGreatnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        MageObjectReference sourceRef = new MageObjectReference(source);
        int manaValue = game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT, controller.getId(), game)
                .stream()
                .filter(Objects::nonNull)
                .filter(permanent -> !sourceRef.refersTo(permanent, game))
                .mapToInt(MageObject::getManaValue)
                .sum();
        FilterCard filter = new FilterPermanentCard();
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, manaValue + 1));
        return CardUtil.castSpellWithAttributesForFree(
                controller, source, game, new CardsImpl(controller.getHand()), filter
        ) || controller.scry(1, source, game);
    }
}
