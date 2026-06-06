package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HeartflameDuelist extends AdventureCard {

    private static final FilterNonlandCard filter = new FilterNonlandCard("instant and sorcery spells you control");

    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    public HeartflameDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.KNIGHT}, "{1}{W}",
                "Heartflame Slash",
                new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Heartflame Duelist
        this.getLeftHalfCard().setPT(3, 1);

        // Instant and sorcery spells you control have lifelink.
        Effect effect = new GainAbilityControlledSpellsEffect(LifelinkAbility.getInstance(), filter);
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(effect));

        // Heartflame Slash
        // Heartflame Slash deals 3 damage to any target.
        this.getRightHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetAnyTarget());

        finalizeCard();
    }

    private HeartflameDuelist(final HeartflameDuelist card) {
        super(card);
    }

    @Override
    public HeartflameDuelist copy() {
        return new HeartflameDuelist(this);
    }
}
