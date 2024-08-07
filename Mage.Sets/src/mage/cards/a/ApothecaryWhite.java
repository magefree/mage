package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.HumanToken;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class ApothecaryWhite extends CardImpl {

    public ApothecaryWhite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you attack, you create a Food token for each player being attacked.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new CreateTokenEffect(new FoodToken(), ApothecaryWhiteValue.instance)
                        .setText("you create a Food token for each player being attacked."), 1));
        // {W}, {T}, Tap X untapped Foods you control: Create X 1/1 white Human creature tokens.
        Ability ability = new SimpleActivatedAbility(new ApothecaryWhiteEffect(), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ApothecaryWhiteCost());
        this.addAbility(ability);
    }

    private ApothecaryWhite(final ApothecaryWhite card) {
        super(card);
    }

    @Override
    public ApothecaryWhite copy() {
        return new ApothecaryWhite(this);
    }

    enum ApothecaryWhiteValue implements DynamicValue {
        instance;

        @Override
        public int calculate(Game game, Ability sourceAbility, Effect effect) {
            return game
                    .getCombat()
                    .getGroups()
                    .stream()
                    .filter(combatGroup -> combatGroup
                            .getAttackers()
                            .stream()
                            .map(game::getControllerId)
                            .anyMatch(sourceAbility::isControlledBy))
                    .map(CombatGroup::getDefenderId)
                    .filter(Objects::nonNull)
                    .distinct()
                    .map(game::getPlayer)
                    .filter(Objects::nonNull)
                    .mapToInt(x -> 1)
                    .sum();
        }

        @Override
        public ApothecaryWhiteValue copy() {
            return this;
        }

        @Override
        public String getMessage() {
            return "player being attacked.";
        }
    }

    class ApothecaryWhiteEffect extends OneShotEffect {

        ApothecaryWhiteEffect() {
            super(Outcome.Benefit);
            staticText = "Create X 1/1 white Human creature tokens";
        }

        private ApothecaryWhiteEffect(final ApothecaryWhiteEffect effect) {
            super(effect);
        }

        @Override
        public ApothecaryWhiteEffect copy() {
            return new ApothecaryWhiteEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null) {
                return false;
            }
            int xValue = CardUtil.getSourceCostsTag(game, source, "X", 0);
            if (xValue < 1) {
                return false;
            }
            new HumanToken().putOntoBattlefield(xValue, game, source);
            return true;
        }
    }
}

class ApothecaryWhiteCost extends VariableCostImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.FOOD, "untapped Foods you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    ApothecaryWhiteCost() {
        super(VariableCostType.NORMAL, "controlled untapped Foods you would like to tap");
        this.text = "Tap X untapped Foods you control";
    }

    private ApothecaryWhiteCost(final ApothecaryWhiteCost cost) {
        super(cost);
    }

    @Override
    public ApothecaryWhiteCost copy() {
        return new ApothecaryWhiteCost(this);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return game.getBattlefield().count(filter, source.getControllerId(), source, game);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new TapTargetCost(new TargetControlledPermanent(xValue, xValue, filter, true));
    }
}
