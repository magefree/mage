package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class RabidAttack extends CardImpl {

    public RabidAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Until end of turn, any number of target creatures you control each get +1/+0 and gain "When this creature dies, draw a card."
        Ability ability = new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1));

        Effect effect = new BoostTargetEffect(1, 0);
        effect.setText("Until end of turn, any number of target creatures you control each get +1/+0");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(ability)
            .setText("and gain \"When this creature dies, draw a card.\" until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE));
    }

    private RabidAttack(final RabidAttack card) {
        super(card);
    }

    @Override
    public RabidAttack copy() {
        return new RabidAttack(this);
    }
}
