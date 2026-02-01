package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.*;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.choices.ChoiceCreatureType;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author TheElk801
 */
public final class CelestialReunion extends CardImpl {

    public CelestialReunion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // As an additional cost to cast this spell, you may choose a creature type and behold two creatures of that type.
        this.addAbility(new CelestialReunionAbility());

        // Search your library for a creature card with mana value X or less, reveal it, put it into your hand, then shuffle. If this spell's additional cost was paid and the revealed card is the chosen type, put that card onto the battlefield instead of putting it into your hand.
        this.getSpellAbility().addEffect(new CelestialReunionEffect());
    }

    private CelestialReunion(final CelestialReunion card) {
        super(card);
    }

    @Override
    public CelestialReunion copy() {
        return new CelestialReunion(this);
    }
}

class CelestialReunionAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private final String rule;

    public static final String CELESTIAL_REUNION_ACTIVATION_VALUE_KEY = "beholdActivation";

    protected OptionalAdditionalCost additionalCost;

    public static OptionalAdditionalCost makeCost() {
        OptionalAdditionalCost cost = new OptionalAdditionalCostImpl(
                "As an additional cost to cast this spell, you may choose a creature type and behold two creatures of that type",
                "Choose a creature you control or reveal a creature card from your hand of the chosen type.",
                new CelestialReunionCost()
        );
        cost.setRepeatable(false);
        return cost;
    }

    public CelestialReunionAbility() {
        super(Zone.STACK, null);
        this.additionalCost = makeCost();
        this.rule = additionalCost.getName() + ". " + additionalCost.getReminderText();
        this.setRuleAtTheTop(true);
    }

    private CelestialReunionAbility(final CelestialReunionAbility ability) {
        super(ability);
        this.rule = ability.rule;
        this.additionalCost = ability.additionalCost.copy();
    }

    @Override
    public CelestialReunionAbility copy() {
        return new CelestialReunionAbility(this);
    }

    public void resetCost() {
        if (additionalCost != null) {
            additionalCost.reset();
        }
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (!(ability instanceof SpellAbility)) {
            return;
        }

        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return;
        }

        this.resetCost();
        boolean canPay = additionalCost.canPay(ability, this, ability.getControllerId(), game);
        if (!canPay || !player.chooseUse(Outcome.Exile, "Choose a creature type and behold two creatures of that type?", ability, game)) {
            return;
        }

        additionalCost.activate();
        for (Cost cost : ((Costs<Cost>) additionalCost)) {
            ability.getCosts().add(cost.copy());
        }
        ability.setCostsTag(CELESTIAL_REUNION_ACTIVATION_VALUE_KEY, null);
    }

    @Override
    public String getCastMessageSuffix() {
        return additionalCost.getCastSuffixMessage(0);
    }

    @Override
    public String getRule() {
        return rule;
    }

    static boolean checkCard(Card card, Game game, Ability source) {
        return CardUtil
                .checkSourceCostsTagExists(game, source, CELESTIAL_REUNION_ACTIVATION_VALUE_KEY)
                && CardUtil
                .castStream(source.getCosts(), CelestialReunionCost.class)
                .map(CelestialReunionCost::getSubType)
                .filter(Objects::nonNull)
                .anyMatch(subType -> card.hasSubtype(subType, game));
    }
}

class CelestialReunionCost extends CostImpl {

    private SubType subType = null;

    CelestialReunionCost() {
        super();
    }

    private CelestialReunionCost(final CelestialReunionCost cost) {
        super(cost);
        this.subType = cost.subType;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Set<Card> cards = Optional
                .ofNullable(controllerId)
                .map(game::getPlayer)
                .map(Player::getHand)
                .map(x -> x.getCards(StaticFilters.FILTER_CARD_CREATURE, game))
                .orElseGet(HashSet::new);
        cards.addAll(game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE, controllerId, source, game));
        return CardUtil.checkAnyPairs(cards, (c1, c2) -> c1.shareCreatureTypes(game, c2));
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            paid = false;
            return paid;
        }
        ChoiceCreatureType choice = new ChoiceCreatureType(game, source);
        player.choose(Outcome.Benefit, choice, game);
        SubType subType = SubType.byDescription(choice.getChoice());
        if (subType == null) {
            paid = false;
            return paid;
        }
        this.subType = subType;
        this.paid = BeholdType.getBeholdType(subType).doBehold(player, 2, game, source).size() == 2;
        return paid;
    }

    @Override
    public CelestialReunionCost copy() {
        return new CelestialReunionCost(this);
    }

    public SubType getSubType() {
        return subType;
    }
}

class CelestialReunionEffect extends OneShotEffect {

    enum CelestialReunionPredicate implements ObjectSourcePlayerPredicate<Card> {
        instance;

        @Override
        public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
            return input.getObject().getManaValue() <= GetXValue.instance.calculate(game, input.getSource(), null);
        }
    }

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value X or less");

    static {
        filter.add(CelestialReunionPredicate.instance);
    }

    CelestialReunionEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for a creature card with mana value X or less, reveal it, " +
                "put it into your hand, then shuffle. If this spell's additional cost was paid and the revealed card " +
                "is the chosen type, put that card onto the battlefield instead of putting it into your hand";
    }

    private CelestialReunionEffect(final CelestialReunionEffect effect) {
        super(effect);
    }

    @Override
    public CelestialReunionEffect copy() {
        return new CelestialReunionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        if (card == null) {
            player.shuffleLibrary(source, game);
            return true;
        }
        player.revealCards(source, new CardsImpl(card), game);
        player.moveCards(
                card,
                CelestialReunionAbility.checkCard(card, game, source) ? Zone.BATTLEFIELD : Zone.HAND,
                source, game
        );
        player.shuffleLibrary(source, game);
        return true;
    }
}
