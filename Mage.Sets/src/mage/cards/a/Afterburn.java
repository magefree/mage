package mage.cards.a;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class Afterburn extends CardImpl {

    public Afterburn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");
        

        // Choose One -
        //   Target creature gains haste and first strike until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("Target creature gains haste"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn).setText("and first strike until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        //   Remove target creature from combat.
        Mode mode = new Mode(new RemoveFromCombatTargetEffect());
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private Afterburn(final Afterburn card) {
        super(card);
    }

    @Override
    public Afterburn copy() {
        return new Afterburn(this);
    }
}
