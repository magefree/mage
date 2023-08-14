package mage.cards.i;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class InspiringStatuary extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonartifact spells you cast");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(Predicates.not(new AbilityPredicate(ImproviseAbility.class))); // So there are not redundant copies being added to each card
    }

    public InspiringStatuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Non-artifact spells you cast have improvise.
        ImproviseAbility improviseAbility = new ImproviseAbility();
        improviseAbility.setRuleAtTheTop(false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledSpellsEffect(improviseAbility, filter)));

    }

    private InspiringStatuary(final InspiringStatuary card) {
        super(card);
    }

    @Override
    public InspiringStatuary copy() {
        return new InspiringStatuary(this);
    }
}
