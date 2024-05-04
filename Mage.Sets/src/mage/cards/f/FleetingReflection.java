package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FleetingReflection extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creature");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public FleetingReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Target creature you control gains hexproof until end of turn. Untap that creature. Until end of turn, it becomes a copy of up to one other target creature.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, filter, false).setTargetTag(2));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new FleetingReflectionEffect());
    }

    private FleetingReflection(final FleetingReflection card) {
        super(card);
    }

    @Override
    public FleetingReflection copy() {
        return new FleetingReflection(this);
    }
}

class FleetingReflectionEffect extends OneShotEffect {

    FleetingReflectionEffect() {
        super(Outcome.Neutral);
        staticText = "Until end of turn, it becomes a copy of up to one other target creature";
    }

    private FleetingReflectionEffect(final FleetingReflectionEffect effect) {
        super(effect);
    }

    @Override
    public FleetingReflectionEffect copy() {
        return new FleetingReflectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent copyTo = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent copyFrom = game.getPermanentOrLKIBattlefield(source.getTargets().get(1).getFirstTarget());
        if (copyTo == null || copyFrom == null) {
            return false;
        }
        game.copyPermanent(Duration.EndOfTurn, copyFrom, copyTo.getId(), source, new EmptyCopyApplier());
        return true;
    }
}
