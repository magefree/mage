
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 * @author magenoxx_at_gmail.com
 */
public final class HeroesReunion extends CardImpl {

    public HeroesReunion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}{W}");


        // Target player gains 7 life.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(7));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private HeroesReunion(final HeroesReunion card) {
        super(card);
    }

    @Override
    public HeroesReunion copy() {
        return new HeroesReunion(this);
    }
}
