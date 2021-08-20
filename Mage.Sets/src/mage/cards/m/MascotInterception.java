package mage.cards.m;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MascotInterception extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public MascotInterception(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // This spell costs {3} less to cast if it targets a creature token.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(3, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Gain control of target creature until end of turn. Untap that creature. It gets +2/+0 and gains haste until end of turn.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 0, Duration.EndOfTurn
        ).setText("It gets +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains haste until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MascotInterception(final MascotInterception card) {
        super(card);
    }

    @Override
    public MascotInterception copy() {
        return new MascotInterception(this);
    }
}
