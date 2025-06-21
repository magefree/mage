package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class FriendlyRivalry extends CardImpl {

    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent("other target legendary creature you control");

    static {
        filter2.add(new AnotherTargetPredicate(2));
        filter2.add(SuperType.LEGENDARY.getPredicate());
    }

    public FriendlyRivalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{G}");

        // Target creature you control and up to one other target legendary creature you control each deal damage equal to their power to target creature you don't control.
        this.getSpellAbility().addEffect(new FriendlyRivalryEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(1).withChooseHint("to deal damage"));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter2).setTargetTag(2).withChooseHint("to deal damage"));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL).setTargetTag(3).withChooseHint("to take damage"));
    }

    private FriendlyRivalry(final FriendlyRivalry card) {
        super(card);
    }

    @Override
    public FriendlyRivalry copy() {
        return new FriendlyRivalry(this);
    }
}

class FriendlyRivalryEffect extends OneShotEffect {

    FriendlyRivalryEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature you control and up to one other target legendary " +
                "creature you control each deal damage equal to their power to target creature you don't control.";
    }

    private FriendlyRivalryEffect(final FriendlyRivalryEffect effect) {
        super(effect);
    }

    @Override
    public FriendlyRivalryEffect copy() {
        return new FriendlyRivalryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int size = source.getTargets().size();
        if (size < 3) {
            throw new IllegalArgumentException("Wrong code usage. Lost targets list, must has 3, but found: " + source.getTargets());
        }

        List<Permanent> toDealDamage = new ArrayList<>();
        source.getTargets().getTargetsByTag(1).stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .forEach(toDealDamage::add);
        source.getTargets().getTargetsByTag(2).stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .forEach(toDealDamage::add);
        Permanent toTakeDamage = source.getTargets().getTargetsByTag(3).stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
        if (toDealDamage.isEmpty() || toTakeDamage == null) {
            return false;
        }

        toDealDamage.forEach(permanent -> {
            toTakeDamage.damage(permanent.getPower().getValue(), permanent.getId(), source, game, false, true);
        });

        return true;
    }
}
