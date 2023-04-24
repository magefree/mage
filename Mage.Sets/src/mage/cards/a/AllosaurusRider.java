
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class AllosaurusRider extends CardImpl {

    private static final FilterCard filter = new FilterCard("green cards");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public AllosaurusRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may exile two green cards from your hand rather than pay Allosaurus Rider's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(2, filter))));

        // Allosaurus Rider's power and toughness are each equal to 1 plus the number of lands you control.
        DynamicValue onePlusControlledLands = new IntPlusDynamicValue(1, new PermanentsOnBattlefieldCount(new FilterControlledLandPermanent("lands you control")));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(onePlusControlledLands, Duration.EndOfGame)));

    }

    private AllosaurusRider(final AllosaurusRider card) {
        super(card);
    }

    @Override
    public AllosaurusRider copy() {
        return new AllosaurusRider(this);
    }
}
