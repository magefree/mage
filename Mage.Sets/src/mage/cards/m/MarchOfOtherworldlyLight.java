package mage.cards.m;

import mage.ObjectColor;
import mage.abilities.costs.costadjusters.ExileCardsFromHandAdjuster;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarchOfOtherworldlyLight extends CardImpl {

    private static final FilterCard filter = new FilterCard("white cards from your hand");

    private static final FilterPermanent filter2 = new FilterPermanent("artifact, creature, or enchantment");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter2.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public MarchOfOtherworldlyLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}");

        // As an additional cost to cast this spell, you may exile any number of white cards from your hand. This spell costs {2} less to cast for each card exiled this way.
        ExileCardsFromHandAdjuster.addAdjusterAndMessage(this, filter);

        // Exile target artifact, creature, or enchantment with mana value X or less.
        this.getSpellAbility().addEffect(new ExileTargetEffect(
                "exile target artifact, creature, or enchantment with mana value X or less"
        ));
        this.getSpellAbility().setTargetAdjuster(new XManaValueTargetAdjuster(ComparisonType.OR_LESS));
        this.getSpellAbility().addTarget(new TargetPermanent(filter2));
    }

    private MarchOfOtherworldlyLight(final MarchOfOtherworldlyLight card) {
        super(card);
    }

    @Override
    public MarchOfOtherworldlyLight copy() {
        return new MarchOfOtherworldlyLight(this);
    }
}
