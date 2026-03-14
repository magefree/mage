package mage.cards.m;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class MatzalantliTheGreatDoor extends TransformingDoubleFacedCard {

    private static final Hint hint = new ValueHint("Permanent types in graveyard", MatzalantliTheGreatDoorValue.instance);
    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENT);

    public MatzalantliTheGreatDoor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{3}",
                "The Core",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // Matzalantli, the Great Door
        // {T}: Draw a card, then discard a card.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new DrawDiscardControllerEffect(), new TapSourceCost()));

        // {4}, {T}: Transform Matzalantli, the Great Door. Activate only if there are four or more permanent types among cards in your graveyard.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new TransformSourceEffect(), new GenericManaCost(4), new MatzalantliTheGreatDoorCondition()
        );
        ability.addCost(new TapSourceCost());
        ability.addHint(hint);
        this.getLeftHalfCard().addAbility(ability);

        // The Core
        // Fathomless descent -- {T}: Add X mana of any one color, where X is the number of permanent cards in your graveyard.
        this.getRightHalfCard().addSuperType(SuperType.LEGENDARY);
        Ability manaAbility = new DynamicManaAbility(
                Mana.AnyMana(1), xValue, new TapSourceCost(),
                "Add X mana of any one color, where X is the number of permanent cards in your graveyard.", true
        );
        manaAbility.setAbilityWord(AbilityWord.FATHOMLESS_DESCENT);
        manaAbility.addHint(DescendCondition.getHint());
        this.getRightHalfCard().addAbility(manaAbility);
    }

    private MatzalantliTheGreatDoor(final MatzalantliTheGreatDoor card) {
        super(card);
    }

    @Override
    public MatzalantliTheGreatDoor copy() {
        return new MatzalantliTheGreatDoor(this);
    }
}

class MatzalantliTheGreatDoorCondition extends IntCompareCondition {

    MatzalantliTheGreatDoorCondition() {
        super(ComparisonType.OR_GREATER, 4);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        return MatzalantliTheGreatDoorValue.instance.calculate(game, source, null);
    }

    @Override
    public String toString() {
        return "if there are four or more permanent types among cards in your graveyard";
    }
}

enum MatzalantliTheGreatDoorValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller == null) {
            return 0;
        }

        return controller
                .getGraveyard()
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .flatMap(card -> card.getCardType(game).stream())
                .filter(CardType::isPermanentType)
                .collect(Collectors.toSet())
                .size();
    }

    @Override
    public MatzalantliTheGreatDoorValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }
}
