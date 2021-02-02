package mage.cards.b;

import mage.abilities.abilityword.StriveAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BlindingFlare extends CardImpl {

    public BlindingFlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Strive — Blinding Flare costs {R} more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{R}"));

        // Any number of target creatures can't block this turn.
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
    }

    private BlindingFlare(final BlindingFlare card) {
        super(card);
    }

    @Override
    public BlindingFlare copy() {
        return new BlindingFlare(this);
    }
}
