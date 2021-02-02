
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.AddCombatAndMainPhaseEffect;
import mage.abilities.effects.common.UntapAllThatAttackedEffect;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author anonymous
 */
public final class WavesOfAggression extends CardImpl {

    public WavesOfAggression(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R/W}{R/W}");

        // Untap all creatures that attacked this turn. After this main phase, there is an additional combat phase followed by an additional main phase.
        this.getSpellAbility().addWatcher(new AttackedThisTurnWatcher());
        this.getSpellAbility().addEffect(new UntapAllThatAttackedEffect());
        this.getSpellAbility().addEffect(new AddCombatAndMainPhaseEffect());
        // Retrace
        this.addAbility(new RetraceAbility(this));
    }

    private WavesOfAggression(final WavesOfAggression card) {
        super(card);
    }

    @Override
    public WavesOfAggression copy() {
        return new WavesOfAggression(this);
    }
}
