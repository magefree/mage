package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BecomeDayAsEntersAbility;
import mage.abilities.common.BecomesDayOrNightTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunstreakPhoenix extends CardImpl {

    public SunstreakPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If it's nether day nor night, it becomes day when Sunstreak Phoenix enters the battlefield.
        this.addAbility(new BecomeDayAsEntersAbility());

        // When day becomes night or night becomes day, you may pay {1}{R}. If you do, return Sunstreak Phoenix from your graveyard to the battlefield tapped.
        this.addAbility(new BecomesDayOrNightTriggeredAbility(
                Zone.GRAVEYARD,
                new DoIfCostPaid(
                        new ReturnSourceFromGraveyardToBattlefieldEffect(true, false),
                        new ManaCostsImpl<>("{1}{R}")
                )
        ));
    }

    private SunstreakPhoenix(final SunstreakPhoenix card) {
        super(card);
    }

    @Override
    public SunstreakPhoenix copy() {
        return new SunstreakPhoenix(this);
    }
}
