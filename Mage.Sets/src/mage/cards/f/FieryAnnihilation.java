package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.Targets;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FieryAnnihilation extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.EQUIPMENT, "Equipment attached to that creature");

    static {
        filter.add(FieryAnnihilationPredicate.instance);
    }

    public FieryAnnihilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Fiery Annihilation deals 5 damage to target creature. Exile up to one target Equipment attached to that creature. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(
                5, true, "target creature", true
        ));
        this.getSpellAbility().addEffect(new ExileTargetEffect()
                .setTargetPointer(new SecondTargetPointer())
                .setText("exile up to one target Equipment attached to that creature"));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter));
    }

    private FieryAnnihilation(final FieryAnnihilation card) {
        super(card);
    }

    @Override
    public FieryAnnihilation copy() {
        return new FieryAnnihilation(this);
    }
}

enum FieryAnnihilationPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
                .ofNullable(input)
                .map(ObjectSourcePlayer::getSource)
                .map(Ability::getTargets)
                .map(Targets::getFirstTarget)
                .map(uuid -> uuid.equals(input.getObject().getAttachedTo()))
                .orElse(false);
    }
}
