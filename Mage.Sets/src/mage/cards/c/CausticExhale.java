package mage.cards.c;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.BeholdDragonCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CausticExhale extends CardImpl {

    public CausticExhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // As an additional cost to cast this spell, behold a Dragon or pay {1}.
        this.getSpellAbility().addCost(new OrCost(
                "behold a Dragon or pay {1}",
                new BeholdDragonCost(), new GenericManaCost(1)
        ));

        // Target creature gets -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, -3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private CausticExhale(final CausticExhale card) {
        super(card);
    }

    @Override
    public CausticExhale copy() {
        return new CausticExhale(this);
    }
}
