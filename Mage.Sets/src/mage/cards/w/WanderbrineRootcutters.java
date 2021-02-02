
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class WanderbrineRootcutters extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("green creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public WanderbrineRootcutters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U/B}{U/B}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Wanderbrine Rootcutters can't be blocked by green creatures.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

    }

    private WanderbrineRootcutters(final WanderbrineRootcutters card) {
        super(card);
    }

    @Override
    public WanderbrineRootcutters copy() {
        return new WanderbrineRootcutters(this);
    }
}
