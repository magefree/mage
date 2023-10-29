package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class MatzalantliTheGreatDoor extends CardImpl {

    private static final Hint hint = new ValueHint("Permanent types in graveyard", MatzalantliTheGreatDoorValue.instance);

    public MatzalantliTheGreatDoor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.secondSideCardClazz = mage.cards.t.TheCore.class;

        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Draw a card, then discard a card.
        this.addAbility(new SimpleActivatedAbility(new DrawDiscardControllerEffect(), new TapSourceCost()));

        // {4}, {T}: Transform Matzalantli, the Great Door. Activate only if there are four or more permanent types among cards in your graveyard.
        this.addAbility(new TransformAbility());
        Ability ability = new ConditionalActivatedAbility(
                new TransformSourceEffect(),
                new GenericManaCost(4),
                new MatzalantliTheGreatDoorCondition()
        );
        ability.addCost(new TapSourceCost());
        ability.addHint(hint);
        this.addAbility(ability);
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

    MatzalantliTheGreatDoorValue() {
    }

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