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
package mage.sets.fifthdawn;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author jeffwadsworth
 */
public class MycosynthGolem extends CardImpl {

    public MycosynthGolem(UUID ownerId) {
        super(ownerId, 137, "Mycosynth Golem", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{11}");
        this.expansionSetCode = "5DN";
        this.subtype.add("Golem");

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Artifact creature spells you cast have affinity for artifacts.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MycosynthGolemEffect()));
        
    }

    public MycosynthGolem(final MycosynthGolem card) {
        super(card);
    }

    @Override
    public MycosynthGolem copy() {
        return new MycosynthGolem(this);
    }
}

class MycosynthGolemEffect extends ReplacementEffectImpl {

    public MycosynthGolemEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Artifact creature spells you cast have affinity for artifacts";
    }

    public MycosynthGolemEffect(final MycosynthGolemEffect effect) {
        super(effect);
    }

    @Override
    public MycosynthGolemEffect copy() {
        return new MycosynthGolemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        if (object != null) {
            Card card = (Card) object;
            Ability ability = new AffinityForArtifactsAbility();
            game.getState().addOtherAbility(card, ability);
            ability.setControllerId(source.getControllerId());
            ability.setSourceId(card.getId());
            game.getState().addAbility(ability, source.getSourceId(), card);
        }
        return false;
    }
    
    @Override    
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }       

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ((event.getType() == GameEvent.EventType.CAST_SPELL)
                && event.getPlayerId() == source.getControllerId()) {
            MageObject spellObject = game.getObject(event.getSourceId());
            if (spellObject != null
                    && spellObject.getCardType().contains(CardType.CREATURE)
                    && spellObject.getCardType().contains(CardType.ARTIFACT)) {
                return true;
            }
        }
        return false;
    }
}
