
package mage.cards.t;

import java.util.UUID;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Wehk
 */
public final class Twinstrike extends CardImpl {

    public Twinstrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}{R}");

        // Twinstrike deals 2 damage to each of two target creatures.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(2),
                new InvertCondition(HellbentCondition.instance),
                "{this} deals 2 damage to each of two target creatures"));        
        // Hellbent - Cackling Flames deals 5 damage to that creature or player instead if you have no cards in hand.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DestroyTargetEffect(),
                HellbentCondition.instance,
                "<br/><br/><i>Hellbent</i> &mdash; Destroy those creatures instead if you have no cards in hand"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2,2));
     
    }

    private Twinstrike(final Twinstrike card) {
        super(card);
    }

    @Override
    public Twinstrike copy() {
        return new Twinstrike(this);
    }
}
