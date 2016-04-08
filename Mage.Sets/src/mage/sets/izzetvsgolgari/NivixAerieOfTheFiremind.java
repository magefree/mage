/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.izzetvsgolgari;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class NivixAerieOfTheFiremind extends CardImpl {

    public NivixAerieOfTheFiremind(UUID ownerId) {
        super(ownerId, 36, "Nivix, Aerie of the Firemind", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "DDJ";

        // {tap}: Add {C} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        
        // {2}{U}{R}, {tap}: Exile the top card of your library. Until your next turn, you may cast that card if it's an instant or sorcery card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NivixAerieOfTheFiremindEffect(), new ManaCostsImpl<>("{2}{U}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public NivixAerieOfTheFiremind(final NivixAerieOfTheFiremind card) {
        super(card);
    }

    @Override
    public NivixAerieOfTheFiremind copy() {
        return new NivixAerieOfTheFiremind(this);
    }
}

class NivixAerieOfTheFiremindEffect extends OneShotEffect {
    
    NivixAerieOfTheFiremindEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile the top card of your library. Until your next turn, you may cast that card if it's an instant or sorcery card";
    }
    
    NivixAerieOfTheFiremindEffect(final NivixAerieOfTheFiremindEffect effect) {
        super(effect);
    }
    
    @Override
    public NivixAerieOfTheFiremindEffect copy() {
        return new NivixAerieOfTheFiremindEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Library library = controller.getLibrary();
            if (library.size() > 0) {
                Card card = library.removeFromTop(game);
                if (card != null
                        && controller.moveCardsToExile(card, source, game, true, source.getSourceId(), "Nivix, Aerie of the Firemind")
                        && (card.getCardType().contains(CardType.INSTANT) || card.getCardType().contains(CardType.SORCERY))) {
                    ContinuousEffect effect = new NivixAerieOfTheFiremindCanCastEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class NivixAerieOfTheFiremindCanCastEffect extends AsThoughEffectImpl {

    NivixAerieOfTheFiremindCanCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.UntilYourNextTurn, Outcome.Benefit);
        staticText = "Until your next turn, you may cast that card";
    }

    NivixAerieOfTheFiremindCanCastEffect(final NivixAerieOfTheFiremindCanCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public NivixAerieOfTheFiremindCanCastEffect copy() {
        return new NivixAerieOfTheFiremindCanCastEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return this.getTargetPointer().getFirst(game, source) != null
                && this.getTargetPointer().getFirst(game, source).equals(sourceId)
                && source.getControllerId().equals(affectedControllerId)
                && game.getState().getZone(sourceId).equals(Zone.EXILED);
    }
}
