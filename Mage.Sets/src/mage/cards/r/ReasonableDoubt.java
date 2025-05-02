package mage.cards.r;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.SuspectTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReasonableDoubt extends CardImpl {

    public ReasonableDoubt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell unless its controller pays {2}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(2)));
        this.getSpellAbility().addTarget(new TargetSpell());

        // Suspect up to one target creature.
        this.getSpellAbility().addEffect(new SuspectTargetEffect().setTargetPointer(new SecondTargetPointer()).concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
    }

    private ReasonableDoubt(final ReasonableDoubt card) {
        super(card);
    }

    @Override
    public ReasonableDoubt copy() {
        return new ReasonableDoubt(this);
    }
}
