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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class DragonHunter extends CardImpl {

    public DragonHunter(UUID ownerId) {
        super(ownerId, 10, "Dragon Hunter", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from Dragons
        this.addAbility(new ProtectionAbility(new FilterPermanent("Dragon", "Dragons")));

        // Dragon Hunter can block Dragons as though it had reach.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockDragonsAsThoughtIthadReachEffect(Duration.WhileOnBattlefield)));

    }

    public DragonHunter(final DragonHunter card) {
        super(card);
    }

    @Override
    public DragonHunter copy() {
        return new DragonHunter(this);
    }
}
class CanBlockDragonsAsThoughtIthadReachEffect extends AsThoughEffectImpl {

    public CanBlockDragonsAsThoughtIthadReachEffect(Duration duration) {
        super(AsThoughEffectType.BLOCK_DRAGON, duration, Outcome.Benefit);
        staticText = "{this} can block Dragons as though it had reach";
    }

    public CanBlockDragonsAsThoughtIthadReachEffect(final CanBlockDragonsAsThoughtIthadReachEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CanBlockDragonsAsThoughtIthadReachEffect copy() {
        return new CanBlockDragonsAsThoughtIthadReachEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return sourceId.equals(source.getSourceId());
    }

}
