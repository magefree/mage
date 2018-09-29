
package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class NoxiousGhoul extends CardImpl {

    public NoxiousGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Noxious Ghoul or another Zombie enters the battlefield, all non-Zombie creatures get -1/-1 until end of turn.
        this.addAbility(new NoxiousGhoulTriggeredAbility());
    }

    public NoxiousGhoul(final NoxiousGhoul card) {
        super(card);
    }

    @Override
    public NoxiousGhoul copy() {
        return new NoxiousGhoul(this);
    }
}

class NoxiousGhoulTriggeredAbility extends EntersBattlefieldAllTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Zombie");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(Predicates.not(
                new SubtypePredicate(SubType.ZOMBIE)));
    }

    NoxiousGhoulTriggeredAbility() {
        super(
                new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter, false),
                StaticFilters.FILTER_PERMANENT, "Whenever {this} or another Zombie enters the battlefield, " +
                        "all non-Zombie creatures get -1/-1 until end of turn."
        );
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                return permanent.hasSubtype(SubType.ZOMBIE, game)
                        || permanent.getId().equals(sourceId);
            }
        }
        return false;
    }
}