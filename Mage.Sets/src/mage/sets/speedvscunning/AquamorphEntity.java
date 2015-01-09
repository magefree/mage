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
package mage.sets.speedvscunning;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AquamorphEntity extends CardImpl {

    public AquamorphEntity(UUID ownerId) {
        super(ownerId, 54, "Aquamorph Entity", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "DDN";
        this.subtype.add("Shapeshifter");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Aquamorph Entity enters the battlefield or is turned face up, it becomes your choice of 5/1 or 1/5.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new AquamorphEntityReplacementEffect());
        ability.setWorksFaceDown(true);
        this.addAbility(ability);


        // Morph {2}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{2}{U}")));
    }

    public AquamorphEntity(final AquamorphEntity card) {
        super(card);
    }

    @Override
    public AquamorphEntity copy() {
        return new AquamorphEntity(this);
    }
}


class AquamorphEntityReplacementEffect extends ReplacementEffectImpl {

    private final String choice51 = "a 5/1 creature";
    private final String choice15 = "a 1/5 creature";

    public AquamorphEntityReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as {this} enters the battlefield or is turned face up, it becomes your choice of 5/1 or 1/5";
    }

    public AquamorphEntityReplacementEffect(AquamorphEntityReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch(event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
            case TURNFACEUP:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ENTERS_THE_BATTLEFIELD) {
            if (event.getTargetId().equals(source.getSourceId())) {
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null && !sourcePermanent.isFaceDown(game)) {
                    return true;
                }
            }
        }
        if (event.getType().equals(EventType.TURNFACEUP)) {
            if (event.getTargetId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose what the creature becomes to");
            choice.getChoices().add(choice51);
            choice.getChoices().add(choice15);
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                while(!choice.isChosen()) {
                    controller.choose(Outcome.Neutral, choice, game);
                    if (!controller.isInGame()) {
                        return false;
                    }
                }
            }
            MageObject mageObject;
            if (permanent instanceof PermanentCard) {
                mageObject = ((PermanentCard) permanent).getCard();
            } else {
                mageObject = ((PermanentToken) permanent).getToken();
            }
            switch (choice.getChoice()) {
                case choice51:
                    mageObject.getPower().setValue(5);
                    mageObject.getToughness().setValue(1);
                    break;
                case choice15:
                    mageObject.getPower().setValue(1);
                    mageObject.getToughness().setValue(5);
                    break;
            }
        }
        return false;
        
    }

    @Override
    public AquamorphEntityReplacementEffect copy() {
        return new AquamorphEntityReplacementEffect(this);
    }

}
