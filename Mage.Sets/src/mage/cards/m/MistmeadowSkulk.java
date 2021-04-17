
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;

/**
 *
 * @author North
 */
public final class MistmeadowSkulk extends CardImpl {

    private static final FilterCard filter = new FilterCard("mana value 3 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 2));
    }

    public MistmeadowSkulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(LifelinkAbility.getInstance());
        this.addAbility(new ProtectionAbility(filter));
    }

    private MistmeadowSkulk(final MistmeadowSkulk card) {
        super(card);
    }

    @Override
    public MistmeadowSkulk copy() {
        return new MistmeadowSkulk(this);
    }
}
