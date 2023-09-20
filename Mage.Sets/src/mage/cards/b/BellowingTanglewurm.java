

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Loki
 */
public final class BellowingTanglewurm extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("green creatures");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public BellowingTanglewurm (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(IntimidateAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(IntimidateAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)));
    }

    private BellowingTanglewurm(final BellowingTanglewurm card) {
        super(card);
    }

    @Override
    public BellowingTanglewurm copy() {
        return new BellowingTanglewurm(this);
    }

}
