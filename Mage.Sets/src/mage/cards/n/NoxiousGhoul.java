
package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class NoxiousGhoul extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterPermanent();

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(Predicates.not(new SubtypePredicate(SubType.ZOMBIE)));
        filter2.add(NoxiousGhoulPredicate.instance);
    }

    public NoxiousGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Noxious Ghoul or another Zombie enters the battlefield, all non-Zombie creatures get -1/-1 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter, false),
                filter2, "Whenever {this} or another Zombie enters the battlefield, " +
                "all non-Zombie creatures get -1/-1 until end of turn."
        ));
    }

    public NoxiousGhoul(final NoxiousGhoul card) {
        super(card);
    }

    @Override
    public NoxiousGhoul copy() {
        return new NoxiousGhoul(this);
    }
}

enum NoxiousGhoulPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Permanent>> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getObject().hasSubtype(SubType.ZOMBIE, game)
                || input.getObject().getId().equals(input.getSourceId());
    }
}