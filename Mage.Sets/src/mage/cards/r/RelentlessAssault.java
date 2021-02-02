
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.AddCombatAndMainPhaseEffect;
import mage.abilities.effects.common.UntapAllThatAttackedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author Quercitron
 */
public final class RelentlessAssault extends CardImpl {

    public RelentlessAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");

        // Untap all creatures that attacked this turn. After this main phase, there is an additional combat phase followed by an additional main phase.
        this.getSpellAbility().addWatcher(new AttackedThisTurnWatcher());
        this.getSpellAbility().addEffect(new UntapAllThatAttackedEffect());
        this.getSpellAbility().addEffect(new AddCombatAndMainPhaseEffect());
    }

    private RelentlessAssault(final RelentlessAssault card) {
        super(card);
    }

    @Override
    public RelentlessAssault copy() {
        return new RelentlessAssault(this);
    }
}
