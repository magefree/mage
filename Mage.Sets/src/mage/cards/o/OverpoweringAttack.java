package mage.cards.o;

import java.util.UUID;

import mage.abilities.effects.common.AddCombatAndMainPhaseEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.keyword.FreerunningAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackedThisTurnPredicate;

/**
 *
 * @author balazskristof
 */
public final class OverpoweringAttack extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AttackedThisTurnPredicate.instance);
    }

    public OverpoweringAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Freerunning {2}{R}
        this.addAbility(new FreerunningAbility("{2}{R}"));

        // Untap all creatures you control that attacked this turn. If it's your main phase, there is an additional combat phase after this phase, followed by an additional main phase.
        this.getSpellAbility().addEffect(new UntapAllControllerEffect(filter, "Untap all creatures you control that attacked this turn"));
        this.getSpellAbility().addEffect(new AddCombatAndMainPhaseEffect().setText("If it's your main phase, there is an additional combat phase after this phase, followed by an additional main phase"));
    }

    private OverpoweringAttack(final OverpoweringAttack card) {
        super(card);
    }

    @Override
    public OverpoweringAttack copy() {
        return new OverpoweringAttack(this);
    }
}