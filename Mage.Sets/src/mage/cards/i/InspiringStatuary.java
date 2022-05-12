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

import java.util.UUID;

/**
 * @author Styxo
 */
public final class InspiringStatuary extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonartifact spells");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public InspiringStatuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Non-artifact spells you cast have improvise.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledSpellsEffect(new ImproviseAbility(), filter)));

    }

    private InspiringStatuary(final InspiringStatuary card) {
        super(card);
    }

    @Override
    public InspiringStatuary copy() {
        return new InspiringStatuary(this);
    }
}
