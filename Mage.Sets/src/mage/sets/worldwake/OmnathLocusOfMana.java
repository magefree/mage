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
package mage.sets.worldwake;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.ManaType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManaTypeInManaPoolCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class OmnathLocusOfMana extends CardImpl<OmnathLocusOfMana> {

    public OmnathLocusOfMana(UUID ownerId) {
        super(ownerId, 109, "Omnath, Locus of Mana", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "WWK";
        this.supertype.add("Legendary");
        this.subtype.add("Elemental");
        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Green mana doesn't empty from your mana pool as steps and phases end.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new OmnathReplacementEffect()));

        // Omnath, Locus of Mana gets +1/+1 for each green mana in your mana pool
        DynamicValue boost = new ManaTypeInManaPoolCount(ManaType.GREEN);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostSourceEffect(boost, boost, Duration.WhileOnBattlefield)));

    }

    public OmnathLocusOfMana(final OmnathLocusOfMana card) {
        super(card);
    }

    @Override
    public OmnathLocusOfMana copy() {
        return new OmnathLocusOfMana(this);
    }
}

class OmnathReplacementEffect extends ReplacementEffectImpl<OmnathReplacementEffect> {
    
    private static final List<ManaType> manaTypes =  new ArrayList<ManaType>();
    static {
        manaTypes.add(ManaType.BLACK);
        manaTypes.add(ManaType.BLUE);
        manaTypes.add(ManaType.RED);
        manaTypes.add(ManaType.WHITE);
        manaTypes.add(ManaType.COLORLESS);
    }

    public OmnathReplacementEffect() {
        super(Duration.WhileOnBattlefield, Constants.Outcome.BoostCreature);
        staticText = "Green mana doesn't empty from your mana pool as steps and phases end";
    }

    public OmnathReplacementEffect(final OmnathReplacementEffect effect) {
        super(effect);
    }

    @Override
    public OmnathReplacementEffect copy() {
        return new OmnathReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null){
            player.getManaPool().emptyManaType(manaTypes);
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.EMPTY_MANA_POOL && event.getPlayerId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }
}