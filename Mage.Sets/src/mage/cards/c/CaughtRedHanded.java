package mage.cards.c;

import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.common.SuspectTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaughtRedHanded extends CardImpl {

    public CaughtRedHanded(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn. Suspect it.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn."));
        this.getSpellAbility().addEffect(new SuspectTargetEffect().setText("suspect it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private CaughtRedHanded(final CaughtRedHanded card) {
        super(card);
    }

    @Override
    public CaughtRedHanded copy() {
        return new CaughtRedHanded(this);
    }
}
