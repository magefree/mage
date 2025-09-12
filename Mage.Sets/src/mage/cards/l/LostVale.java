package mage.cards.l;

import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LostVale extends CardImpl {

    public LostVale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.nightCard = true;

        // T: Add three mana of any one color.
        this.addAbility(new SimpleManaAbility(new AddManaOfAnyColorEffect(3), new TapSourceCost()));
    }

    private LostVale(final LostVale card) {
        super(card);
    }

    @Override
    public LostVale copy() {
        return new LostVale(this);
    }
}
