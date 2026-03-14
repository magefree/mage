package mage.cards.m;

import java.util.UUID;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.MutagenToken;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class MutantChainReaction extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, enchantment, or creature with flying");

    static {
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.ENCHANTMENT.getPredicate(),
            Predicates.and(
                CardType.CREATURE.getPredicate(),
                new AbilityPredicate(FlyingAbility.class)
            )
        ));
    }

    public MutantChainReaction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Destroy up to one target artifact, enchantment, or creature with flying. Create a Mutagen token.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new MutagenToken()));
    }

    private MutantChainReaction(final MutantChainReaction card) {
        super(card);
    }

    @Override
    public MutantChainReaction copy() {
        return new MutantChainReaction(this);
    }
}
