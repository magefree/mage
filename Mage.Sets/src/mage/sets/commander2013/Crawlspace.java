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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.RulesEffects;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class Crawlspace extends CardImpl<Crawlspace> {

    public Crawlspace(UUID ownerId) {
        super(ownerId, 240, "Crawlspace", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "C13";

        // No more than two creatures can attack you each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChangeMaxAttackedBySourceEffect(2)));

    }

    public Crawlspace(final Crawlspace card) {
        super(card);
    }

    @Override
    public Crawlspace copy() {
        return new Crawlspace(this);
    }
}

class ChangeMaxAttackedBySourceEffect extends ContinuousEffectImpl<ChangeMaxAttackedBySourceEffect> {

    private final int maxAttackedBy;

    public ChangeMaxAttackedBySourceEffect(int maxAttackedBy) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.maxAttackedBy = maxAttackedBy;
        staticText = "No more than two creatures can attack you each combat";
    }

    public ChangeMaxAttackedBySourceEffect(final ChangeMaxAttackedBySourceEffect effect) {
        super(effect);
        this.maxAttackedBy = effect.maxAttackedBy;
    }

    @Override
    public ChangeMaxAttackedBySourceEffect copy() {
        return new ChangeMaxAttackedBySourceEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case RulesEffects:
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    // Change the rule
                    if (controller.getMaxAttackedBy()> maxAttackedBy) {
                        controller.setMaxAttackedBy(maxAttackedBy);
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
