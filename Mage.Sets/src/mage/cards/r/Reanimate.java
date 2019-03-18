
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.TargetConvertedManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class Reanimate extends CardImpl {

    public Reanimate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());  
        Effect effect = new LoseLifeSourceControllerEffect(TargetConvertedManaCost.instance);
        effect.setText("You lose life equal to its converted mana cost");
        getSpellAbility().addEffect(effect);  
    }

    public Reanimate(final Reanimate card) {
        super(card);
    }

    @Override
    public Reanimate copy() {
        return new Reanimate(this);
    }
}
