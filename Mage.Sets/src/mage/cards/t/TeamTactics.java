package mage.cards.t;

import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.TeamworkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class TeamTactics extends CardImpl {

    public TeamTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Teamwork 1
        this.addAbility(new TeamworkAbility(1));

        // Target creature gains double strike until end of turn. If this spell was cast using teamwork, that creature also gains trample until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()));
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
            new GainAbilityTargetEffect(TrampleAbility.getInstance()),
            TeamworkCondition.instance,
            "If this spell was cast using teamwork, that creature also gains trample until end of turn"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TeamTactics(final TeamTactics card) {
        super(card);
    }

    @Override
    public TeamTactics copy() {
        return new TeamTactics(this);
    }
}
