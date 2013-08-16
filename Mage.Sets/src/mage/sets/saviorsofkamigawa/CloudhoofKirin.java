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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterSpiritOrArcaneCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class CloudhoofKirin extends CardImpl<CloudhoofKirin> {

    public CloudhoofKirin(UUID ownerId) {
        super(ownerId, 31, "Cloudhoof Kirin", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "SOK";
        this.supertype.add("Legendary");
        this.subtype.add("Kirin");
        this.subtype.add("Spirit");

        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a Spirit or Arcane spell, you may have target player put the top X cards of his or her library into his or her graveyard, where X is that spell's converted mana cost.
        Ability ability = new SpellCastTriggeredAbility(Zone.BATTLEFIELD, new CloudhoofKirinEffect(), new FilterSpiritOrArcaneCard(), true, true);
        ability.addTarget(new TargetPlayer(true));
        this.addAbility(ability);
    }

    public CloudhoofKirin(final CloudhoofKirin card) {
        super(card);
    }

    @Override
    public CloudhoofKirin copy() {
        return new CloudhoofKirin(this);
    }
}

class CloudhoofKirinEffect extends OneShotEffect<CloudhoofKirinEffect> {

    public CloudhoofKirinEffect() {
        super(Outcome.Detriment);
        this.staticText = "you may have target player put the top X cards of his or her library into his or her graveyard, where X is that spell's converted mana cost";
    }

    public CloudhoofKirinEffect(final CloudhoofKirinEffect effect) {
        super(effect);
    }

    @Override
    public CloudhoofKirinEffect copy() {
        return new CloudhoofKirinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player targetPlayer = null;
            for(Target target: source.getTargets()) {
                if (target instanceof TargetPlayer) {
                    targetPlayer = game.getPlayer(target.getFirstTarget());
                }
            }
            if (targetPlayer != null) {
                int cardsCount = Math.min(spell.getManaCost().convertedManaCost(), targetPlayer.getLibrary().size());
                for (int i = 0; i < cardsCount; i++) {
                    Card card = targetPlayer.getLibrary().removeFromTop(game);
                    if (card != null) {
                        card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                    }
                    else {
                        break;
                    }
                }
                return true;
            }

        }
        return false;
    }
}
