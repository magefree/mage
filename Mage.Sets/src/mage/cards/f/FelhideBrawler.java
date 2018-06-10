
package mage.cards.f;

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
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class FelhideBrawler extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another Minotaur");

    static {
        filter.add(new SubtypePredicate(SubType.MINOTAUR));
        filter.add(new AnotherPredicate());
    }

    public FelhideBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.MINOTAUR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Felhide Brawler can't block unless you control another Minotaur.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockUnlessYouControlSourceEffect(filter)));
    }

    public FelhideBrawler(final FelhideBrawler card) {
        super(card);
    }

    @Override
    public FelhideBrawler copy() {
        return new FelhideBrawler(this);
    }
}
