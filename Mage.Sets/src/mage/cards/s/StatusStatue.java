package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class StatusStatue extends SplitCard {

    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or enchantment");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public StatusStatue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B/G}", "{2}{B}{G}", SpellAbilityType.SPLIT);

        // Status
        // Target creature gets +1/+1 and gains deathtouch until end of turn.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new BoostTargetEffect(1, 1, Duration.EndOfTurn)
                        .setText("Target creature gets +1/+1")
        );
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new GainAbilityTargetEffect(
                        DeathtouchAbility.getInstance(),
                        Duration.EndOfTurn
                ).setText("and gains deathtouch until end of turn")
        );
        this.getLeftHalfCard().getSpellAbility().addTarget(
                new TargetCreaturePermanent()
        );

        // Statue
        // Destroy target artifact, creature, or enchantment.
        this.getRightHalfCard().getSpellAbility().addEffect(
                new DestroyTargetEffect()
        );
        this.getRightHalfCard().getSpellAbility().addTarget(
                new TargetPermanent(filter)
        );
    }

    private StatusStatue(final StatusStatue card) {
        super(card);
    }

    @Override
    public StatusStatue copy() {
        return new StatusStatue(this);
    }
}
