
package mage.cards.t;

import mage.Mana;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class TurnToDust extends CardImpl {

    public TurnToDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_EQUIPMENT));
        this.getSpellAbility().addEffect(new BasicManaEffect(Mana.GreenMana(1)));
    }

    private TurnToDust(final TurnToDust card) {
        super(card);
    }

    @Override
    public TurnToDust copy() {
        return new TurnToDust(this);
    }
}
