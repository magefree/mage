package mage.cards.o;

import mage.abilities.effects.common.DamageTargetAndYouEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class OrcishCannonade extends CardImpl {

    public OrcishCannonade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}{R}");

        // Orcish Cannonade deals 2 damage to any target and 3 damage to you.
        this.getSpellAbility().addEffect(new DamageTargetAndYouEffect(2, 3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private OrcishCannonade(final OrcishCannonade card) {
        super(card);
    }

    @Override
    public OrcishCannonade copy() {
        return new OrcishCannonade(this);
    }
}
