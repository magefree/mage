
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostSourceWhileControlsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author TheElk801
 */
public final class KumenasSpeaker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another Merfolk or an Island");

    static {
        filter.add(new AnotherPredicate());
        filter.add(Predicates.or(
                new SubtypePredicate(SubType.ISLAND),
                new SubtypePredicate(SubType.MERFOLK)));
    }

    public KumenasSpeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kumena's Omenspeaker gets +1/+1 as long as you control another Merfolk or Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceWhileControlsEffect(filter, 1, 1)));
    }

    public KumenasSpeaker(final KumenasSpeaker card) {
        super(card);
    }

    @Override
    public KumenasSpeaker copy() {
        return new KumenasSpeaker(this);
    }
}
