package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapVariableTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.HumanToken;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class ApothecaryWhite extends CardImpl {

    static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.FOOD, "untapped Foods you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

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
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new HumanToken(), GetXValue.instance), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapVariableTargetCost(filter));
        this.addAbility(ability);
    }

    private ApothecaryWhite(final ApothecaryWhite card) {
        super(card);
    }

    @Override
    public ApothecaryWhite copy() {
        return new ApothecaryWhite(this);
    }
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
