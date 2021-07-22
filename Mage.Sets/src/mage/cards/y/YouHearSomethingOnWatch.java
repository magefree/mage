package mage.cards.y;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YouHearSomethingOnWatch extends CardImpl {

    public YouHearSomethingOnWatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Choose one —
        // • Rouse the Party — Creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        this.getSpellAbility().withFirstModeFlavorWord("Rouse the Party");

        // • Set Off Traps — This spell deals 5 damage to target attacking creature.
        Mode mode = new Mode(new DamageTargetEffect(5, "this spell"));
        mode.addTarget(new TargetAttackingCreature());
        this.getSpellAbility().addMode(mode.withFlavorWord("Set Off Traps"));
    }

    private YouHearSomethingOnWatch(final YouHearSomethingOnWatch card) {
        super(card);
    }

    @Override
    public YouHearSomethingOnWatch copy() {
        return new YouHearSomethingOnWatch(this);
    }
}
