package mage.cards.r;

import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.TeamworkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class RepulsorBlast extends CardImpl {

    public RepulsorBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Teamwork 2
        this.addAbility(new TeamworkAbility(2));

        // Repulsor Blast deals 5 damage to target creature. If this spell was cast using teamwork, it also deals 2 damage to that creature's controller.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new DamageTargetControllerEffect(2),
            TeamworkCondition.instance,
            "If this spell was cast using teamwork, it also deals 2 damage to that creature's controller"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RepulsorBlast(final RepulsorBlast card) {
        super(card);
    }

    @Override
    public RepulsorBlast copy() {
        return new RepulsorBlast(this);
    }
}
