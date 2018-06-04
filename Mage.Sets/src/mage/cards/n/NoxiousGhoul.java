
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class NoxiousGhoul extends CardImpl {

    final FilterPermanent filter = new FilterPermanent("Noxious Ghoul or another Zombie");
    final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("non-Zombie");

    public NoxiousGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        filter.add(Predicates.or(
                new CardIdPredicate(this.getId()),
                new SubtypePredicate(SubType.ZOMBIE)));

        filter2.add(new CardTypePredicate(CardType.CREATURE));
        filter2.add(Predicates.not(
                new SubtypePredicate(SubType.ZOMBIE)));

        final String rule = "Whenever {this} or another Zombie enters the battlefield, all non-Zombie creatures get -1/-1 until end of turn.";

        // Whenever Noxious Ghoul or another Zombie enters the battlefield, all non-Zombie creatures get -1/-1 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter2, false), filter, false, rule));
    }

    public NoxiousGhoul(final NoxiousGhoul card) {
        super(card);
    }

    @Override
    public NoxiousGhoul copy() {
        return new NoxiousGhoul(this);
    }
}
