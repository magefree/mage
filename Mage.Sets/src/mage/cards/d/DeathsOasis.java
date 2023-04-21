package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathsOasis extends CardImpl {

    public DeathsOasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{B}{G}");

        // Whenever a nontoken creature you control dies, put the top two cards of your library into your graveyard. Then return a creature card with lesser converted mana cost than the creature that died from the graveyard to your hand.
        this.addAbility(new DeathsOasisTriggeredAbility());

        // {1}, Sacrifice Death's Oasis: You gain life equal to the greatest converted mana cost among creatures you control.
        Ability ability = new SimpleActivatedAbility(
                new GainLifeEffect(DeathsOasisValue.instance)
                        .setText("you gain life equal to the highest mana value among creatures you control"),
                new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private DeathsOasis(final DeathsOasis card) {
        super(card);
    }

    @Override
    public DeathsOasis copy() {
        return new DeathsOasis(this);
    }
}

class DeathsOasisTriggeredAbility extends DiesCreatureTriggeredAbility {

    DeathsOasisTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false, StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN, false);
    }

    private DeathsOasisTriggeredAbility(final DeathsOasisTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DeathsOasisTriggeredAbility copy() {
        return new DeathsOasisTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getTarget() == null) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new MillCardsControllerEffect(2));
        this.addEffect(new DeathsOasisEffect(zEvent.getTarget().getManaValue()));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken creature you control dies, mill two cards. " +
                "Then return a creature card with lesser mana value " +
                "than the creature that died from your graveyard to your hand.";
    }
}

class DeathsOasisEffect extends OneShotEffect {

    private final FilterCard filter;

    DeathsOasisEffect(int cmc) {
        super(Outcome.Benefit);
        this.filter = new FilterCreatureCard("creature card in your graveyard with mana value " + (cmc - 1) + " or less");
        this.filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, cmc));
    }

    private DeathsOasisEffect(final DeathsOasisEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public DeathsOasisEffect copy() {
        return new DeathsOasisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(filter, game) == 0) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(filter);
        target.setNotTarget(true);
        if (!player.choose(outcome, player.getGraveyard(), target, source, game)) {
            return false;
        }
        return player.moveCards(game.getCard(target.getFirstTarget()), Zone.HAND, source, game);
    }
}

enum DeathsOasisValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getAllActivePermanents(sourceAbility.getControllerId())
                .stream()
                .filter(permanent -> permanent.isCreature(game))
                .mapToInt(Permanent::getManaValue)
                .max()
                .orElse(0);
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "1";
    }
}
