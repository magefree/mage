
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class MorbidBloom extends CardImpl {

    public MorbidBloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{G}");


        

        // Exile target creature card from a graveyard, then create X 1/1 green Saproling creature tokens, where X is the exiled card's toughness.
        this.getSpellAbility().addEffect(new MorbidBloomEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature from a graveyard")));
        
    }

    public MorbidBloom(final MorbidBloom card) {
        super(card);
    }

    @Override
    public MorbidBloom copy() {
        return new MorbidBloom(this);
    }
}

class MorbidBloomEffect extends OneShotEffect {

    public MorbidBloomEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Exile target creature card from a graveyard, then create X 1/1 green Saproling creature tokens, where X is the exiled card's toughness";
    }

    public MorbidBloomEffect(final MorbidBloomEffect effect) {
        super(effect);
    }

    @Override
    public MorbidBloomEffect copy() {
        return new MorbidBloomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card targetCreatureCard = game.getCard(source.getFirstTarget());
        if (targetCreatureCard != null) {
            targetCreatureCard.moveToExile(null, null, source.getSourceId(), game);
            int toughness = targetCreatureCard.getToughness().getValue();
            SaprolingToken token = new SaprolingToken();
            return token.putOntoBattlefield(toughness, game, source.getSourceId(), source.getControllerId());
        }
        return false;
    }
}
