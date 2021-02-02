
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBlockUnlessYouControlSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author North
 */
public final class MindlessNull extends CardImpl {
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Vampire");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
    }
    public MindlessNull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockUnlessYouControlSourceEffect(filter)));
    }

    private MindlessNull(final MindlessNull card) {
        super(card);
    }

    @Override
    public MindlessNull copy() {
        return new MindlessNull(this);
    }
}
