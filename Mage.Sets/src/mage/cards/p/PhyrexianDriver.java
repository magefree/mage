
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author Backfir3
 */
public final class PhyrexianDriver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Mercenary creatures");

    static {
        filter.add(SubType.MERCENARY.getPredicate());
    }

    public PhyrexianDriver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
		
		//When Phyrexian Driver enters the battlefield, other Mercenary creatures get +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, true)));
    }

    private PhyrexianDriver(final PhyrexianDriver card) {
        super(card);
    }

    @Override
    public PhyrexianDriver copy() {
        return new PhyrexianDriver(this);
    }
}
