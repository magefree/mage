
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Galatolol
 */
public final class PhyrexianReaper extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("green creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public PhyrexianReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Phyrexian Reaper becomes blocked by a green creature, destroy that creature. It can't be regenerated.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new DestroyTargetEffect(true), filter, false));
    }

    private PhyrexianReaper(final PhyrexianReaper card) {
        super(card);
    }

    @Override
    public PhyrexianReaper copy() {
        return new PhyrexianReaper(this);
    }
}
