
package mage.cards.h;

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
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class HowlpackWolf extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another Wolf or Werewolf");

    static {
        filter.add(Predicates.or(SubType.WOLF.getPredicate(), SubType.WEREWOLF.getPredicate()));
        filter.add(AnotherPredicate.instance);
    }

    public HowlpackWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Howlpack Wolf can't block unless you control another Wolf or Werewolf.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockUnlessYouControlSourceEffect(filter)));
    }

    private HowlpackWolf(final HowlpackWolf card) {
        super(card);
    }

    @Override
    public HowlpackWolf copy() {
        return new HowlpackWolf(this);
    }
}
