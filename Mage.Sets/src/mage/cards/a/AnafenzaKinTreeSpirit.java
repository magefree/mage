
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author fireshoes
 */
public final class AnafenzaKinTreeSpirit extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another nontoken creature");

    static {
        filter.add(Predicates.not(new TokenPredicate()));
        filter.add(new AnotherPredicate());
    }

    public AnafenzaKinTreeSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another nontoken creature enters the battlefield under your control, bolster 1.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new BolsterEffect(1), filter, false));
    }

    public AnafenzaKinTreeSpirit(final AnafenzaKinTreeSpirit card) {
        super(card);
    }

    @Override
    public AnafenzaKinTreeSpirit copy() {
        return new AnafenzaKinTreeSpirit(this);
    }
}
