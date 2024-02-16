
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Blinke
 */
public final class SpoilsOfEvil extends CardImpl {
    private static final FilterCard filter = new FilterCard("artifact or creature card in target opponents graveyard");
    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()));
    }
    
    public SpoilsOfEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        // For each artifact or creature card in target opponent's graveyard, add {C} and you gain 1 life.
        this.getSpellAbility().addEffect(new SpoilsOfEvilEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private SpoilsOfEvil(final SpoilsOfEvil card) {
        super(card);
    }

    @Override
    public SpoilsOfEvil copy() {
        return new SpoilsOfEvil(this);
    }
    
    static class SpoilsOfEvilEffect extends OneShotEffect {

        public SpoilsOfEvilEffect() {
            super(Outcome.GainLife);
            this.staticText = "For each artifact or creature card in target opponent's graveyard, add {C} and you gain 1 life.";
        }
        
        private SpoilsOfEvilEffect(final SpoilsOfEvilEffect effect) {
            super(effect);
        }
        
        @Override
        public SpoilsOfEvilEffect copy() {
            return new SpoilsOfEvilEffect(this);
        }
        
        @Override
        public boolean apply(Game game, Ability source) {
            Player targetOpponent = game.getPlayer(targetPointer.getFirst(game, source));
            Player controller = game.getPlayer(source.getControllerId());
            
            if(targetOpponent != null && controller != null) {
                int cardCount = targetOpponent.getGraveyard().count(filter, game);
                controller.gainLife(cardCount, game, source);
                controller.getManaPool().addMana(Mana.ColorlessMana(cardCount), game, source);
                return true;
            }
            return false;
        }
    }
}
