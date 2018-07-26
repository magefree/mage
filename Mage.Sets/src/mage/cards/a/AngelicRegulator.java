package mage.cards.a;


import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.RenownedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardSetInfo;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 *
 * @author EikePeace
 */
public final class AngelicRegulator extends CardImpl {

    public AngelicRegulator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        //Flying
        this.addAbility(FlyingAbility.getInstance());

        //Renown
        this.addAbility(new RenownAbility(1));

        //As long as Angelic Regulator is renowned, other creatures you control get +1/+1.
        Effect effect = new ConditionalContinuousEffect(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, true), RenownedSourceCondition.instance,
                "As long as {this} is renowned, other creatures you control get +1/1.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }


    public AngelicRegulator(final AngelicRegulator card) {
        super(card);
    }

    @Override
    public AngelicRegulator copy() {
        return new AngelicRegulator(this);
    }
}
