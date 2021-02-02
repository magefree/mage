
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ruleModifying.CantRegenerateTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author LevelX2
 */
public final class RageOfPurphoros extends CardImpl {

    public RageOfPurphoros(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}");


        // Rage of Purphoros deals 4 damage to target creature. It can't be regenerated this turn. Scry 1.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CantRegenerateTargetEffect(Duration.EndOfTurn, "It"));
        this.getSpellAbility().addEffect(new ScryEffect(1));

        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));
    }

    private RageOfPurphoros(final RageOfPurphoros card) {
        super(card);
    }

    @Override
    public RageOfPurphoros copy() {
        return new RageOfPurphoros(this);
    }
}
