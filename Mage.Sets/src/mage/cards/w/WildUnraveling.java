package mage.cards.w;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildUnraveling extends CardImpl {

    public WildUnraveling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // As an additional cost to cast this spell, blight 2 or pay {1}.
        this.getSpellAbility().addCost(new OrCost(
                "blight 2 or pay {1}", new BlightCost(2), new GenericManaCost(2)
        ));

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private WildUnraveling(final WildUnraveling card) {
        super(card);
    }

    @Override
    public WildUnraveling copy() {
        return new WildUnraveling(this);
    }
}
