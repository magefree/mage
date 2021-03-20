
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class AmbushKrotiq extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AmbushKrotiq(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Ambush Krotiq enters the battlefield, return another creature you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter)));

    }

    private AmbushKrotiq(final AmbushKrotiq card) {
        super(card);
    }

    @Override
    public AmbushKrotiq copy() {
        return new AmbushKrotiq(this);
    }
}
