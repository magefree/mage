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
package mage.sets.dissension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Plopman
 */
public class UtopiaSprawl extends CardImpl<UtopiaSprawl> {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Forest");

    static {
        filter.add(new SubtypePredicate("Forest"));
    }
    
    public UtopiaSprawl(UUID ownerId) {
        super(ownerId, 99, "Utopia Sprawl", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.expansionSetCode = "DIS";
        this.subtype.add("Aura");

        this.color.setGreen(true);

        // Enchant Forest
        TargetPermanent auraTarget = new TargetLandPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // As Utopia Sprawl enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect()));
        // Whenever enchanted Forest is tapped for mana, its controller adds one mana of the chosen color to his or her mana pool.
        this.addAbility(new UtopiaSprawlTriggeredAbility());
    }

    public UtopiaSprawl(final UtopiaSprawl card) {
        super(card);
    }

    @Override
    public UtopiaSprawl copy() {
        return new UtopiaSprawl(this);
    }
}

class ChooseColorEffect extends OneShotEffect<ChooseColorEffect> {

    public ChooseColorEffect() {
        super(Constants.Outcome.BoostCreature);
        staticText = "choose a color";
    }

    public ChooseColorEffect(final ChooseColorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            ChoiceColor colorChoice = new ChoiceColor();
            if (player.choose(Constants.Outcome.Neutral, colorChoice, game)) {
                game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + colorChoice.getChoice());
                game.getState().setValue(permanent.getId() + "_color", colorChoice.getColor());
            }
        }
        return false;
    }

    @Override
    public ChooseColorEffect copy() {
        return new ChooseColorEffect(this);
    }

}

class UtopiaSprawlTriggeredAbility extends TriggeredManaAbility<UtopiaSprawlTriggeredAbility> {

   
    public UtopiaSprawlTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UtopiaSprawlEffect());
    }

    public UtopiaSprawlTriggeredAbility(UtopiaSprawlTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if(event.getType() == GameEvent.EventType.TAPPED_FOR_MANA){
            if (enchantment != null && event.getSourceId().equals(enchantment.getAttachedTo())) {
                return true;
            }
        }  
        return false;
    }

    @Override
    public UtopiaSprawlTriggeredAbility copy() {
        return new UtopiaSprawlTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever enchanted Forest is tapped for mana, its controller adds one mana of the chosen color to his or her mana pool";
    }
}


class UtopiaSprawlEffect extends ManaEffect<UtopiaSprawlEffect> {

    public UtopiaSprawlEffect() {
        super();
        staticText = "its controller adds one mana of the chosen color to his or her mana pool";
    }

    public UtopiaSprawlEffect(final UtopiaSprawlEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if(enchantment != null){
            Permanent land = game.getPermanent(enchantment.getAttachedTo());
            if(land != null){
                Player player = game.getPlayer(land.getControllerId());
                if (player != null) {
                    ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
                    if (color.isBlack())
                        player.getManaPool().addMana(Mana.BlackMana, game, source);
                    else if (color.isBlue())
                        player.getManaPool().addMana(Mana.BlueMana, game, source);
                    else if (color.isRed())
                        player.getManaPool().addMana(Mana.RedMana, game, source);
                    else if (color.isGreen())
                        player.getManaPool().addMana(Mana.GreenMana, game, source);
                    else if (color.isWhite())
                        player.getManaPool().addMana(Mana.WhiteMana, game, source);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public UtopiaSprawlEffect copy() {
        return new UtopiaSprawlEffect(this);
    }
}