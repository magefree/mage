
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TimingRule;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author cbt33, BetaSteward (PastInFlames)
 */
public final class Recoup extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("sorcery card");
            
    static{
        filter.add(new CardTypePredicate(CardType.SORCERY));
    }

    public Recoup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Target sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        this.getSpellAbility().addEffect(new RecoupEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        // Flashback {3}{R}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{3}{R}"), TimingRule.SORCERY));
    }

    public Recoup(final Recoup card) {
        super(card);
    }

    @Override
    public Recoup copy() {
        return new Recoup(this);
    }
}

class RecoupEffect extends ContinuousEffectImpl {

    public RecoupEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Target sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost";
    }

    public RecoupEffect(final RecoupEffect effect) {
        super(effect);
    }

    @Override
    public RecoupEffect copy() {
        return new RecoupEffect(this);
    }

   @Override 
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card card = game.getCard(targetPointer.getFirst(game, source));
            if (card != null) {
            FlashbackAbility ability = new FlashbackAbility(card.getManaCost(), TimingRule.SORCERY);
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
            return true;
            }
        }
        return false;
}
    
}