
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author North
 */
public final class OneEyedScarecrow extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures with flying your opponents control");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public OneEyedScarecrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.SCARECROW);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(DefenderAbility.getInstance());
        // Creatures with flying your opponents control get -1/-0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(-1, 0, Duration.WhileOnBattlefield, filter, false)));
    }

    private OneEyedScarecrow(final OneEyedScarecrow card) {
        super(card);
    }

    @Override
    public OneEyedScarecrow copy() {
        return new OneEyedScarecrow(this);
    }
}
