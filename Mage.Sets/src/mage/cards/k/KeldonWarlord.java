
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author TGower
 */
public final class KeldonWarlord extends CardImpl {
    
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Wall creatures you control.");
     static {
    filter.add(Predicates.not(SubType.WALL.getPredicate()));
            }

    public KeldonWarlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Keldon Warlord's power and toughness are each equal to the number of non-Wall creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter))));
    }

    private KeldonWarlord(final KeldonWarlord card) {
        super(card);
    }

    @Override
    public KeldonWarlord copy() {
        return new KeldonWarlord(this);
    }
}
