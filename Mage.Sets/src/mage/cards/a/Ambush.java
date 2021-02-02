
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterBlockingCreature;

/**
 *
 * @author fireshoes
 */
public final class Ambush extends CardImpl {
    
    private static final FilterBlockingCreature filter = new FilterBlockingCreature("Blocking creatures");

    public Ambush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");

        // Blocking creatures gain first strike until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filter, false));
    }

    private Ambush(final Ambush card) {
        super(card);
    }

    @Override
    public Ambush copy() {
        return new Ambush(this);
    }
}
