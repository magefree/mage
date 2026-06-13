package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class CaptainAmericaWingsOfFreedom extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.HERO, "Heroes");

    public CaptainAmericaWingsOfFreedom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}")));

        // Whenever Captain America attacks, each other Hero you control gets +X/+X until end of turn, where X is Captain America's toughness.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(
            SourcePermanentToughnessValue.instance,
            SourcePermanentToughnessValue.instance,
            Duration.EndOfTurn,
            filter,
            true
        )));
    }

    private CaptainAmericaWingsOfFreedom(final CaptainAmericaWingsOfFreedom card) {
        super(card);
    }

    @Override
    public CaptainAmericaWingsOfFreedom copy() {
        return new CaptainAmericaWingsOfFreedom(this);
    }
}
