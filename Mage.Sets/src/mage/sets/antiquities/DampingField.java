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
package mage.sets.antiquities;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionUntapNotMoreThanEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class DampingField extends CardImpl<DampingField> {

    public DampingField(UUID ownerId) {
        super(ownerId, 98, "Damping Field", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.expansionSetCode = "ATQ";

        this.color.setWhite(true);

        // Players can't untap more than one artifact during their untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DampingFieldEffect()));
    }

    public DampingField(final DampingField card) {
        super(card);
    }

    @Override
    public DampingField copy() {
        return new DampingField(this);
    }
}

class DampingFieldEffect extends RestrictionUntapNotMoreThanEffect<DampingFieldEffect> {

    private static final FilterControlledPermanent filter = new FilterControlledArtifactPermanent("an artifact");

    public DampingFieldEffect() {
        super(Duration.WhileOnBattlefield, 1, filter);
        staticText = "Players can't untap more than one artifact during their untap steps";
    }

    public DampingFieldEffect(final DampingFieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Player player, Ability source, Game game) {
        // applies to all players
        return true;
    }

    @Override
    public DampingFieldEffect copy() {
        return new DampingFieldEffect(this);
    }

}
