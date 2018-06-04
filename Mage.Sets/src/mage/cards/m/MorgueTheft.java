
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author cbt33
 */
public final class MorgueTheft extends CardImpl {
    
    public MorgueTheft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");


        // Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard()));
        // Flashback {4}{B}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{4}{B}"), TimingRule.SORCERY));
        
    }

    public MorgueTheft(final MorgueTheft card) {
        super(card);
    }

    @Override
    public MorgueTheft copy() {
        return new MorgueTheft(this);
    }
}
