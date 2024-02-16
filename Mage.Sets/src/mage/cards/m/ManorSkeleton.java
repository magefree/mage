
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class ManorSkeleton extends CardImpl {

    public ManorSkeleton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SKELETON);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(HasteAbility.getInstance());
        // {1}{B}: Regenerate Manor Skeleton.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{B}")));
    }

    private ManorSkeleton(final ManorSkeleton card) {
        super(card);
    }

    @Override
    public ManorSkeleton copy() {
        return new ManorSkeleton(this);
    }
}
