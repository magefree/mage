

package mage.cards.m;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ManaLeak extends CardImpl {

    public ManaLeak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3)));
    }

    private ManaLeak(final ManaLeak card) {
        super(card);
    }

    @Override
    public ManaLeak copy() {
        return new ManaLeak(this);
    }
}
