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
package mage.sets.journeyintonyx;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continious.LoseCreatureTypeSourceEffect;
import mage.abilities.effects.common.continious.MaximumHandSizeControllerEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.ManaPool;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class KruphixGodOfHorizons extends CardImpl<KruphixGodOfHorizons> {

    public KruphixGodOfHorizons(UUID ownerId) {
        super(ownerId, 152, "Kruphix, God of Horizons", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{G}{U}");
        this.expansionSetCode = "JOU";
        this.supertype.add("Legendary");
        this.subtype.add("God");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to green and blue is less than seven, Kruhpix isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.G, ColoredManaSymbol.U), 7);
        effect.setText("As long as your devotion to green and blue is less than seven, Kruhpix isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // You have no maximum hand size.
        effect = new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield, MaximumHandSizeControllerEffect.HandSizeModification.SET);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // If unused mana would empty from your mana pool, that mana becomes colorless instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KruphixGodOfHorizonsEffect()));

    }

    public KruphixGodOfHorizons(final KruphixGodOfHorizons card) {
        super(card);
    }

    @Override
    public KruphixGodOfHorizons copy() {
        return new KruphixGodOfHorizons(this);
    }
}

class KruphixGodOfHorizonsEffect extends ReplacementEffectImpl<KruphixGodOfHorizonsEffect> {

    private static final List<ManaType> manaTypes =  new ArrayList<>();
    static {
        manaTypes.add(ManaType.BLACK);
        manaTypes.add(ManaType.BLUE);
        manaTypes.add(ManaType.RED);
        manaTypes.add(ManaType.WHITE);
        manaTypes.add(ManaType.GREEN);
    }

    public KruphixGodOfHorizonsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If unused mana would empty from your mana pool, that mana becomes colorless instead";
    }

    public KruphixGodOfHorizonsEffect(final KruphixGodOfHorizonsEffect effect) {
        super(effect);
    }

    @Override
    public KruphixGodOfHorizonsEffect copy() {
        return new KruphixGodOfHorizonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null){
            ManaPool pool = player.getManaPool();
            int coloredMana = pool.getGreen() + pool.getBlack() + pool.getBlue()+ pool.getWhite()+ pool.getRed();
            player.getManaPool().emptyManaType(manaTypes);
            pool.addMana(Mana.ColorlessMana(coloredMana), game, source);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getType() == GameEvent.EventType.EMPTY_MANA_POOL && event.getPlayerId().equals(source.getControllerId());
    }
}