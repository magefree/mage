package mage.cards.i;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IrohsDemonstration extends CardImpl {

    public IrohsDemonstration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        this.subtype.add(SubType.LESSON);

        // Choose one --
        // * Iroh's Demonstration deals 1 damage to each creature your opponents control.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));

        // * Iroh's Demonstration deals 4 damage to target creature.
        this.getSpellAbility().addMode(new Mode(new DamageTargetEffect(4)).addTarget(new TargetCreaturePermanent()));
    }

    private IrohsDemonstration(final IrohsDemonstration card) {
        super(card);
    }

    @Override
    public IrohsDemonstration copy() {
        return new IrohsDemonstration(this);
    }
}
