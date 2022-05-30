

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author Backfir3
 */
public final class DriftingDjinn extends CardImpl {

    public DriftingDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.DJINN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{1}{U}")), TargetController.YOU, false));
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private DriftingDjinn(final DriftingDjinn card) {
        super(card);
    }

    @Override
    public DriftingDjinn copy() {
        return new DriftingDjinn(this);
    }
}
