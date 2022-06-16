
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class SigilOfTheNewDawn extends CardImpl {

    public SigilOfTheNewDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Whenever a creature is put into your graveyard from the battlefield, you may pay {1}{W}. If you do, return that card to your hand.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("return that card to your hand");
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new DoIfCostPaid(effect, new ManaCostsImpl<>("{1}{W}")),
                false, StaticFilters.FILTER_PERMANENT_A_CREATURE, true, true
        ));
    }

    private SigilOfTheNewDawn(final SigilOfTheNewDawn card) {
        super(card);
    }

    @Override
    public SigilOfTheNewDawn copy() {
        return new SigilOfTheNewDawn(this);
    }
}
