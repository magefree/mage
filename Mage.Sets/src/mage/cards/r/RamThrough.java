package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RamThrough extends CardImpl {

    public RamThrough(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature you control deals damage equal to its power to target creature you don't control. If the creature you control has trample, excess damage is dealt to that creature's controller instead.
        this.getSpellAbility().addEffect(new RamThroughEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private RamThrough(final RamThrough card) {
        super(card);
    }

    @Override
    public RamThrough copy() {
        return new RamThrough(this);
    }
}

class RamThroughEffect extends OneShotEffect {

    RamThroughEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature you control deals damage equal to its power to target creature you don't control. " +
                "If the creature you control has trample, excess damage is dealt to that creature's controller instead.";
    }

    private RamThroughEffect(final RamThroughEffect effect) {
        super(effect);
    }

    @Override
    public RamThroughEffect copy() {
        return new RamThroughEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() != 2) {
            throw new IllegalStateException("It must have two targets, but found " + source.getTargets().size());
        }

        Permanent myPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent anotherPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());

        if (myPermanent == null || anotherPermanent == null) {
            return false;
        }
        int power = myPermanent.getPower().getValue();
        if (power < 1) {
            return false;
        }
        if (!myPermanent.getAbilities().containsKey(TrampleAbility.getInstance().getId())) {
            return anotherPermanent.damage(power, myPermanent.getId(), source, game, false, true) > 0;
        }
        int lethal = anotherPermanent.getLethalDamage(myPermanent.getId(), game);
        lethal = Math.min(lethal, power);
        anotherPermanent.damage(lethal, myPermanent.getId(), source, game);
        Player player = game.getPlayer(anotherPermanent.getControllerId());
        if (player != null && lethal < power) {
            player.damage(power - lethal, myPermanent.getId(), source, game);
        }
        return true;
    }
}
