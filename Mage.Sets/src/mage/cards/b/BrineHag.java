
package mage.cards.b;

import java.util.UUID;
import java.util.Set;
import java.util.HashSet;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class BrineHag extends CardImpl {

    public BrineHag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.HAG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Brine Hag dies, change the base power and toughness of all creatures that dealt damage to it this turn to 0/2.
        this.addAbility(new DiesSourceTriggeredAbility(new BrineHagEffect()));
    }

    private BrineHag(final BrineHag card) {
        super(card);
    }

    @Override
    public BrineHag copy() {
        return new BrineHag(this);
    }
}

class BrineHagEffect extends OneShotEffect {

    public BrineHagEffect() {
        super(Outcome.Detriment);
        this.staticText = "change the base power and toughness of all creatures that dealt damage to it this turn to 0/2";
    }

    public BrineHagEffect(final BrineHagEffect effect) {
        super(effect);
    }

    @Override
    public BrineHagEffect copy() {
        return new BrineHagEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent == null) {
            return false;
        }

        Set<MageObjectReference> set = new HashSet<>(sourcePermanent.getDealtDamageByThisTurn());
        if (!set.isEmpty()) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new PermanentReferenceInCollectionPredicate(set));
            game.addEffect(new SetBasePowerToughnessAllEffect(0, 2, Duration.Custom, filter, true), source);
        }
        return true;
    }
}
