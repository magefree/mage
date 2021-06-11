
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class PhyrexianSlayer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public PhyrexianSlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Phyrexian Slayer becomes blocked by a white creature, destroy that creature. It can't be regenerated.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new DestroyTargetEffect(true), filter, false));
    }

    private PhyrexianSlayer(final PhyrexianSlayer card) {
        super(card);
    }

    @Override
    public PhyrexianSlayer copy() {
        return new PhyrexianSlayer(this);
    }
}
