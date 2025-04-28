package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScalesOfShale extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.LIZARD, "Lizards");
    private static final Hint hint = new ValueHint("Lizards you control", new PermanentsOnBattlefieldCount(filter));

    public ScalesOfShale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // This spell costs {1} less to cast for each Lizard you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AffinityEffect(filter)).setRuleAtTheTop(true).addHint(hint));

        // Target creature gets +2/+0 and gains lifelink and indestructible until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 0, Duration.EndOfTurn
        ).setText("target creature gets +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains lifelink"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ScalesOfShale(final ScalesOfShale card) {
        super(card);
    }

    @Override
    public ScalesOfShale copy() {
        return new ScalesOfShale(this);
    }
}
