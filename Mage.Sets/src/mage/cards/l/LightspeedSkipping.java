package mage.cards.l;

import mage.abilities.Mode;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAttackingOrBlockingCreature;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class LightspeedSkipping extends CardImpl {
    public LightspeedSkipping(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        //Choose one --
        //* Target creature gains hexproof until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        //* Remove target creature from combat.
        Mode mode = new Mode(new RemoveFromCombatTargetEffect());
        mode.addTarget(new TargetAttackingOrBlockingCreature());
        this.getSpellAbility().addMode(mode);
    }

    public LightspeedSkipping(final LightspeedSkipping card) {
        super(card);
    }

    @Override
    public LightspeedSkipping copy() {
        return new LightspeedSkipping(this);
    }
}
