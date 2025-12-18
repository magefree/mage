package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VincentValentine extends TransformingDoubleFacedCard {

    public VincentValentine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ASSASSIN}, "{2}{B}{B}",
                "Galian Beast",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF, SubType.BEAST}, "B"
        );

        // Vincent Valentine
        this.getLeftHalfCard().setPT(2, 2);

        // Whenever a creature an opponent controls dies, put a number of +1/+1 counters on Vincent Valentine equal to that creature's power.
        this.getLeftHalfCard().addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), VincentValentineValue.instance)
                        .setText("put a number of +1/+1 counters on {this} equal to that creature's power"),
                false, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
        ));

        // Whenever Vincent Valentine attacks, you may transform it.
        this.getLeftHalfCard().addAbility(new AttacksTriggeredAbility(new TransformSourceEffect(), true));

        // Galian Beast
        this.getRightHalfCard().setPT(3, 2);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());

        // When Galian Beast dies, return it to the battlefield tapped.
        this.getRightHalfCard().addAbility(new DiesSourceTriggeredAbility(new ReturnSourceFromGraveyardToBattlefieldEffect(true)
                .setText("return it to the battlefield tapped")));
    }

    private VincentValentine(final VincentValentine card) {
        super(card);
    }

    @Override
    public VincentValentine copy() {
        return new VincentValentine(this);
    }
}

enum VincentValentineValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .of((Permanent) effect.getValue("creatureDied"))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
    }

    @Override
    public VincentValentineValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
