package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author lopho
 */
public final class PoisonbellyOgre extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");
    static {
        filter.add(AnotherPredicate.instance);
    }

    public PoisonbellyOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another creature enters the battlefield, its controller loses 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD,
                new LoseLifeTargetEffect(1).setText("its controller loses 1 life"),
                filter, false, SetTargetPointer.PLAYER));
    }

    private PoisonbellyOgre(final PoisonbellyOgre card) {
        super(card);
    }

    @Override
    public PoisonbellyOgre copy() {
        return new PoisonbellyOgre(this);
    }
}
