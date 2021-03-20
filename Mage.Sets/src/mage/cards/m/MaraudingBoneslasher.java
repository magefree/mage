
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
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class MaraudingBoneslasher extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another Zombie");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public MaraudingBoneslasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MINOTAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Marauding Boneslasher can't block unless you control another Zombie.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockUnlessYouControlSourceEffect(filter)));

    }

    private MaraudingBoneslasher(final MaraudingBoneslasher card) {
        super(card);
    }

    @Override
    public MaraudingBoneslasher copy() {
        return new MaraudingBoneslasher(this);
    }
}
