package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.LifeCompareCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class DivinityOfPride extends CardImpl {

    public DivinityOfPride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W/B}{W/B}{W/B}{W/B}{W/B}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Divinity of Pride gets +4/+4 as long as you have 25 or more life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostSourceEffect(4, 4, Duration.WhileOnBattlefield),
                        new LifeCompareCondition(TargetController.YOU, ComparisonType.OR_GREATER, 25),
                        "{this} gets +4/+4 as long as you have 25 or more life")));
    }

    private DivinityOfPride(final DivinityOfPride card) {
        super(card);
    }

    @Override
    public DivinityOfPride copy() {
        return new DivinityOfPride(this);
    }
}
