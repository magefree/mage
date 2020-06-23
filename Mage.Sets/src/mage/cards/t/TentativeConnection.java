package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TentativeConnection extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("you control a creature with menace");

    static {
        filter.add(new AbilityPredicate(MenaceAbility.class));
    }

    public TentativeConnection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // This spell costs {3} less to cast if you control a creature with menace.
        Ability ability = new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionSourceEffect(
                        3, new PermanentsOnTheBattlefieldCondition(filter)
                )
        );
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("It gains haste until end of turn."));
    }

    private TentativeConnection(final TentativeConnection card) {
        super(card);
    }

    @Override
    public TentativeConnection copy() {
        return new TentativeConnection(this);
    }
}
