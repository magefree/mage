package mage.cards.a;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AtraxasFall extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("artifact, battle, enchantment, or creature with flying");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.BATTLE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                Predicates.and(
                        CardType.CREATURE.getPredicate(),
                        new AbilityPredicate(FlyingAbility.class)
                )
        ));
    }

    public AtraxasFall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Destroy target artifact, battle, enchantment, or creature with flying.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private AtraxasFall(final AtraxasFall card) {
        super(card);
    }

    @Override
    public AtraxasFall copy() {
        return new AtraxasFall(this);
    }
}
