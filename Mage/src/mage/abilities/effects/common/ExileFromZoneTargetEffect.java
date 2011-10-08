/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.abilities.effects.common;

import java.util.UUID;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ExileFromZoneTargetEffect extends OneShotEffect<ExileFromZoneTargetEffect> {

    private Zone zone;
    private FilterCard filter;
    private UUID exileId;
    private String exileName;
    private int amount;
    
    public ExileFromZoneTargetEffect(Zone zone, UUID exileId, String exileName, FilterCard filter) {
        this(zone, exileId, exileName, filter, 1);
    }
    
    public ExileFromZoneTargetEffect(Zone zone, UUID exileId, String exileName, FilterCard filter, int amount) {
        super(Outcome.Exile);
        this.zone = zone;
        this.filter = filter;
        this.exileId = exileId;
        this.exileName = exileName;
        this.amount = amount;
        setText();
    }
    
    public ExileFromZoneTargetEffect(final ExileFromZoneTargetEffect effect) {
        super(effect);
        this.zone = effect.zone;
        this.filter = effect.filter.copy();
        this.exileId = effect.exileId;
        this.exileName = effect.exileName;
        this.amount = effect.amount;
    }
    
	@Override
	public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(source));
        if (player != null) {
            Target target = null;
            switch (zone) {
                case HAND:
                    target = new TargetCardInHand(Math.min(player.getHand().count(filter, game), amount), filter);                    
                    break;
                case GRAVEYARD:
                    target = new TargetCardInGraveyard(Math.min(player.getGraveyard().count(filter, game), amount), filter);
                    break;
                default:
            }
            if (target != null && target.canChoose(player.getId(), game)) {
                if (target.choose(Outcome.Exile, player.getId(), source.getSourceId(), game)) {
                    for (UUID cardId: target.getTargets()) {
                        Card card = game.getCard(cardId);
                        if (card != null)
                            card.moveToExile(exileId, exileName, source.getSourceId(), game);
                    }
                    return true;
                }
            }
        }
        return false;
	}

    @Override
    public ExileFromZoneTargetEffect copy() {
        return new ExileFromZoneTargetEffect(this);
    }
    
	private void setText() {
        if (amount == 1)
            staticText = "Target player exiles a " + filter.getMessage() + " from his or her " + zone.toString();
        else
            staticText = "Target player exiles " + amount + " " + filter.getMessage() + " from his or her " + zone.toString();
    }
    
}
