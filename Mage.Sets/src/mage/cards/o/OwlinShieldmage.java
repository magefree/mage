package mage.cards.o;

import mage.MageInt;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OwlinShieldmage extends CardImpl {

    public OwlinShieldmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward—Pay 3 life
        this.addAbility(new WardAbility(new PayLifeCost(3)));
    }

    private OwlinShieldmage(final OwlinShieldmage card) {
        super(card);
    }

    @Override
    public OwlinShieldmage copy() {
        return new OwlinShieldmage(this);
    }
}
