
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LevelX2
 */
public final class MasterworkOfIngenuity extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("equipment");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new SubtypePredicate(SubType.EQUIPMENT));
    }

    public MasterworkOfIngenuity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // You may have Masterwork of Ingenuity enter the battlefield as a copy of any Equipment on the battlefield.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(filter), true));
    }

    public MasterworkOfIngenuity(final MasterworkOfIngenuity card) {
        super(card);
    }

    @Override
    public MasterworkOfIngenuity copy() {
        return new MasterworkOfIngenuity(this);
    }
}
