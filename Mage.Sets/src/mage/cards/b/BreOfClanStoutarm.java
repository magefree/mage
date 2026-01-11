package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import mage.watchers.common.PlayerGainedLifeWatcher;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class BreOfClanStoutarm extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition();

    public BreOfClanStoutarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {1}{W}, {T}: Another target creature you control gains flying and lifelink until end of turn.
        Ability activatedAbility = new SimpleActivatedAbility(
            new GainAbilityTargetEffect(FlyingAbility.getInstance())
                .setText("another target creature you control gains flying"),
            new ManaCostsImpl<>("{1}{W}")
        );
        activatedAbility.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance()).setText("and lifelink until end of turn"));
        activatedAbility.addCost(new TapSourceCost());
        activatedAbility.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(activatedAbility);

        // At the beginning of your end step, if you gained life this turn, exile cards from the top of your library until you exile a nonland card.
        // You may cast that card without paying its mana cost if the spell's mana value is less than or equal to the amount of life you gained this turn.
        // Otherwise, put it into your hand.
        Ability triggeredAbility = new BeginningOfEndStepTriggeredAbility(new BreOfClanStoutarmEffect()).withInterveningIf(condition);
        this.addAbility(triggeredAbility.addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private BreOfClanStoutarm(final BreOfClanStoutarm card) {
        super(card);
    }

    @Override
    public BreOfClanStoutarm copy() {
        return new BreOfClanStoutarm(this);
    }
}

enum BreOfClanStoutarmPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return input.getObject().getManaValue() <= ControllerGainedLifeCount.instance.calculate(game, input.getSource(), null);
    }

    @Override
    public String toString() {
        return "mana value less than or equal to the amount of life you gained this turn";
    }
}

class BreOfClanStoutarmEffect extends OneShotEffect {
    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(BreOfClanStoutarmPredicate.instance);
    }

   BreOfClanStoutarmEffect() {
        super(Outcome.PlayForFree);
        staticText = "exile cards from the top of your library until you exile a nonland card. "
                + "You may cast that card without paying its mana cost if the spell's mana value is less than "
                + "or equal to the amount of life you gained this turn. Otherwise, put it into your hand";
    }

    private BreOfClanStoutarmEffect(final BreOfClanStoutarmEffect effect) {
        super(effect);
    }

    @Override
    public BreOfClanStoutarmEffect copy() {
        return new BreOfClanStoutarmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = getCard(player, game, source);
        if (card == null) {
            return false;
        }
        if (!CardUtil.castSpellWithAttributesForFree(player, source, game, card, filter)) {
            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }

    private static Card getCard(Player player, Game game, Ability source) {
        for (Card card : player.getLibrary().getCards(game)) {
            player.moveCards(card, Zone.EXILED, source, game);
            game.processAction();
            if (!card.isLand(game)) {
                return card;
            }
        }
        return null;
    }
}
