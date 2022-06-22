package mage.cards.n;

import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NecroticFumes extends CardImpl {

    public NecroticFumes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        this.subtype.add(SubType.LESSON);

        // As an additional cost to cast this spell, exile a creature you control.
        this.getSpellAbility().addCost(new ExileTargetCost(new TargetControlledPermanent(1, 1, StaticFilters.FILTER_CONTROLLED_A_CREATURE, true)));

        // Exile target creature or planeswalker.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private NecroticFumes(final NecroticFumes card) {
        super(card);
    }

    @Override
    public NecroticFumes copy() {
        return new NecroticFumes(this);
    }
}
