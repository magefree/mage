
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author Loki
 */
public final class KnotvinePaladin extends CardImpl {
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }


    public KnotvinePaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);



        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter), new PermanentsOnBattlefieldCount(filter), Duration.EndOfTurn), false));
    }

    private KnotvinePaladin(final KnotvinePaladin card) {
        super(card);
    }

    @Override
    public KnotvinePaladin copy() {
        return new KnotvinePaladin(this);
    }
}
