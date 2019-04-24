
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessConditionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class BlindSpotGiant extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control another Giant");

    static {
        filter.add(new SubtypePredicate(SubType.GIANT));
        filter.add(new AnotherPredicate());
    }

    public BlindSpotGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.GIANT, SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Blind-Spot Giant can't attack or block unless you control another Giant.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new CantAttackBlockUnlessConditionSourceEffect(new PermanentsOnTheBattlefieldCondition(filter))));

    }

    public BlindSpotGiant(final BlindSpotGiant card) {
        super(card);
    }

    @Override
    public BlindSpotGiant copy() {
        return new BlindSpotGiant(this);
    }
}
