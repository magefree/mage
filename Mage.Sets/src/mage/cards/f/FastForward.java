package mage.cards.f;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.AttackedThisTurnOpponentsCount;
import mage.abilities.effects.common.combat.GoadAllEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterOpponentsCreaturePermanent;

/**
 *
 * @author muz
 */
public final class FastForward extends CardImpl {

    private static final FilterOpponentsCreaturePermanent filter = new FilterOpponentsCreaturePermanent("");

    public FastForward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // This spell costs {1} less to cast for each opponent you attacked this turn.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL,
            new SpellCostReductionForEachSourceEffect(1, AttackedThisTurnOpponentsCount.instance)
                .setText("this spell costs {1} less to cast for each opponent you attacked this turn")
        )
        .addHint(new ValueHint("Opponents you attacked this turn", AttackedThisTurnOpponentsCount.instance)));

        // Goad all creatures your opponents control.
        this.getSpellAbility().addEffect(new GoadAllEffect(filter)
            .setText("goad all creatures your opponents control")
        );
    }

    private FastForward(final FastForward card) {
        super(card);
    }

    @Override
    public FastForward copy() {
        return new FastForward(this);
    }
}
