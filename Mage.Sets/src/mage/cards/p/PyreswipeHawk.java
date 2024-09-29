package mage.cards.p;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.ExpendTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PyreswipeHawk extends CardImpl {

    public PyreswipeHawk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Pyreswipe Hawk attacks, it gets +X/+0 until end of turn, where X is the greatest mana value among artifacts you control.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                PyreswipeHawkValue.instance, StaticValue.get(0), Duration.EndOfTurn, "it"
        )));

        // Whenever you expend 6, gain control of up to one target artifact for as long as you control Pyreswipe Hawk.
        Ability ability = new ExpendTriggeredAbility(
                new GainControlTargetEffect(Duration.WhileControlled), ExpendTriggeredAbility.Expend.SIX
        );
        ability.addTarget(new TargetArtifactPermanent(0, 1));
        this.addAbility(ability);
    }

    private PyreswipeHawk(final PyreswipeHawk card) {
        super(card);
    }

    @Override
    public PyreswipeHawk copy() {
        return new PyreswipeHawk(this);
    }
}

enum PyreswipeHawkValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT,
                        sourceAbility.getControllerId(), sourceAbility, game
                )
                .stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
    }

    @Override
    public PyreswipeHawkValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the greatest mana value among artifacts you control";
    }

    @Override
    public String toString() {
        return "X";
    }
}
