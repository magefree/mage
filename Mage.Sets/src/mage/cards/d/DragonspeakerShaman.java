
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author North
 */
public final class DragonspeakerShaman extends CardImpl {

    private static final FilterCard filter = new FilterCard("Dragon spells");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }

    public DragonspeakerShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Dragon spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 2)));
    }

    public DragonspeakerShaman(final DragonspeakerShaman card) {
        super(card);
    }

    @Override
    public DragonspeakerShaman copy() {
        return new DragonspeakerShaman(this);
    }
}
