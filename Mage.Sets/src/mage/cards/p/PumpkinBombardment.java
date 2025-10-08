package mage.cards.p;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PumpkinBombardment extends CardImpl {

    public PumpkinBombardment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B/R}");

        // As an additional cost to cast this spell, discard a card or pay {2}.
        this.getSpellAbility().addCost(new OrCost(
                "discard a card or pay {2}", new DiscardCardCost(), new GenericManaCost(2)
        ));

        // Pumpkin Bombardment deals 3 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PumpkinBombardment(final PumpkinBombardment card) {
        super(card);
    }

    @Override
    public PumpkinBombardment copy() {
        return new PumpkinBombardment(this);
    }
}
