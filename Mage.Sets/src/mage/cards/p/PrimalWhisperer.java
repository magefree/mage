
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;

/**
 *
 * @author LoneFox
 */
public final class PrimalWhisperer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("face-down creature");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public PrimalWhisperer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Primal Whisperer gets +2/+2 for each face-down creature on the battlefield.
        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(filter, 2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(amount, amount, Duration.WhileOnBattlefield)));
        // Morph {3}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{3}{G}")));
    }

    private PrimalWhisperer(final PrimalWhisperer card) {
        super(card);
    }

    @Override
    public PrimalWhisperer copy() {
        return new PrimalWhisperer(this);
    }
}
