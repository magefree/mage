
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author ciaccona007
 */
public final class CrashThrough extends CardImpl {
    
    public CrashThrough(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");
        

        // Creatures you control gain trample until end of turn.
        getSpellAbility().addEffect(new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent(), "Creatures you control gain trample until end of turn"));
        
    // Draw a card.
        getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private CrashThrough(final CrashThrough card) {
        super(card);
    }

    @Override
    public CrashThrough copy() {
        return new CrashThrough(this);
    }
}
