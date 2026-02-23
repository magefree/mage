package mage.cards.b;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BogslithersEmbrace extends CardImpl {

    public BogslithersEmbrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // As an additional cost to cast this spell, blight 1 or pay {3}.
        this.getSpellAbility().addCost(new OrCost(
                "blight 1 or pay {3}", new BlightCost(1), new GenericManaCost(3)
        ));

        // Exile target creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BogslithersEmbrace(final BogslithersEmbrace card) {
        super(card);
    }

    @Override
    public BogslithersEmbrace copy() {
        return new BogslithersEmbrace(this);
    }
}
