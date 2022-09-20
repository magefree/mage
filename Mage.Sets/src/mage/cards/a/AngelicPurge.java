package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class AngelicPurge extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or enchantment");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }

    public AngelicPurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // As an additional cost to cast Angelic Purge, sacrifice a permanent.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledPermanent("a permanent"))));

        // Exile target artifact, creature, or enchantment.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter).withChooseHint("to exile"));
    }

    private AngelicPurge(final AngelicPurge card) {
        super(card);
    }

    @Override
    public AngelicPurge copy() {
        return new AngelicPurge(this);
    }
}
