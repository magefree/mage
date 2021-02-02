
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author fireshoes
 */
public final class ShambleBack extends CardImpl {

    public ShambleBack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        // Exile target creature card from a graveyard. Create a 2/2 black Zombie creature token. You gain 2 life.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken()));
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
    }

    private ShambleBack(final ShambleBack card) {
        super(card);
    }

    @Override
    public ShambleBack copy() {
        return new ShambleBack(this);
    }
}
