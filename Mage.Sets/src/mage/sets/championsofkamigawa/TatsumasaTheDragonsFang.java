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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlSourceEffect;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class TatsumasaTheDragonsFang extends CardImpl<TatsumasaTheDragonsFang> {

    public TatsumasaTheDragonsFang(UUID ownerId) {
        super(ownerId, 270, "Tatsumasa, the Dragon's Fang", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{6}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Equipment");

        // Equipped creature gets +5/+5.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(5, 5)));

        // {6}, Exile Tatsumasa, the Dragon's Fang: Put a 5/5 blue Dragon Spirit creature token with flying onto the battlefield. Return Tatsumasa to the battlefield under its owner's control when that token dies.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TatsumaTheDragonsFangEffect(), new GenericManaCost(6));
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
        
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));
    }

    public TatsumasaTheDragonsFang(final TatsumasaTheDragonsFang card) {
        super(card);
    }

    @Override
    public TatsumasaTheDragonsFang copy() {
        return new TatsumasaTheDragonsFang(this);
    }
}

class TatsumaTheDragonsFangEffect extends OneShotEffect<TatsumaTheDragonsFangEffect> {

    public TatsumaTheDragonsFangEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a 5/5 blue Dragon Spirit creature token with flying onto the battlefield. Return Tatsumasa to the battlefield under its owner's control when that token dies";
    }

    public TatsumaTheDragonsFangEffect(final TatsumaTheDragonsFangEffect effect) {
        super(effect);
    }

    @Override
    public TatsumaTheDragonsFangEffect copy() {
        return new TatsumaTheDragonsFangEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect =  new CreateTokenEffect(new TatsumaDragonToken());
        effect.apply(game, source);
        FixedTarget fixedTarget = new FixedTarget(effect.getLastAddedTokenId());
        DelayedTriggeredAbility delayedAbility = new TatsumaTheDragonsFangTriggeredAbility(fixedTarget);
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);

        return true;
    }
}

class TatsumaTheDragonsFangTriggeredAbility extends DelayedTriggeredAbility<TatsumaTheDragonsFangTriggeredAbility> {

    protected FixedTarget fixedTarget;

    public TatsumaTheDragonsFangTriggeredAbility(FixedTarget fixedTarget) {
        super(new ReturnToBattlefieldUnderYourControlSourceEffect(), Duration.OneUse);
        this.fixedTarget = fixedTarget;
    }

    public TatsumaTheDragonsFangTriggeredAbility(final TatsumaTheDragonsFangTriggeredAbility ability) {
        super(ability);
        this.fixedTarget = ability.fixedTarget;
    }

    @Override
    public TatsumaTheDragonsFangTriggeredAbility copy() {
        return new TatsumaTheDragonsFangTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            if (fixedTarget.getFirst(game, this).equals(event.getTargetId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Return {this} to the battlefield under its owner's control when that token dies." ;
    }
}

class TatsumaDragonToken extends Token {
    public TatsumaDragonToken() {
        super("Dragon Spirit", "5/5 blue Dragon Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.BLUE;
        subtype.add("Dragon");
        subtype.add("Spirit");
        power = new MageInt(5);
        toughness = new MageInt(5);
        addAbility(FlyingAbility.getInstance());
    }
}
