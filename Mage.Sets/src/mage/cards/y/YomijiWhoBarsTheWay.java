
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class YomijiWhoBarsTheWay extends CardImpl {

    public YomijiWhoBarsTheWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a legendary permanent other than Yomiji, Who Bars the Way is put into a graveyard from the battlefield, return that card to its owner's hand.
        FilterPermanent filter = new FilterPermanent("a legendary permanent other than " + getName());
        filter.add(new AnotherPredicate());
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("return that card to its owner's hand");
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(effect, false, filter, true));
    }

    public YomijiWhoBarsTheWay(final YomijiWhoBarsTheWay card) {
        super(card);
    }

    @Override
    public YomijiWhoBarsTheWay copy() {
        return new YomijiWhoBarsTheWay(this);
    }
}
