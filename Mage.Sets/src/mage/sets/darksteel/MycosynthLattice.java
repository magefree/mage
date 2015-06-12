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
package mage.sets.darksteel;

import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Rarity;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * @author duncant
 */
public class MycosynthLattice extends CardImpl {

    public MycosynthLattice(UUID ownerId) {
        super(ownerId, 130, "Mycosynth Lattice", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{6}");
        this.expansionSetCode = "DST";

        // All permanents are artifacts in addition to their other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PermanentsAreArtifactsEffect()));
        // All cards that aren't on the battlefield, spells, and permanents are colorless.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EverythingIsColorlessEffect()));
        // Players may spend mana as though it were mana of any color.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ManaCanBeSpentAsAnyColorEffect()));
    }

    public MycosynthLattice(final MycosynthLattice card) {
        super(card);
    }

    @Override
    public MycosynthLattice copy() {
        return new MycosynthLattice(this);
    }
}

class PermanentsAreArtifactsEffect extends ContinuousEffectImpl {
    
    public PermanentsAreArtifactsEffect(){
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "All permanents are artifacts in addition to their other types";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            List<CardType> cardType = perm.getCardType();
            if (!cardType.contains(CardType.ARTIFACT)) {
                cardType.add(CardType.ARTIFACT);
            }
        }
        return true;
    }

    @Override
    public PermanentsAreArtifactsEffect copy() {
        return new PermanentsAreArtifactsEffect(this);
    }

    private PermanentsAreArtifactsEffect(PermanentsAreArtifactsEffect effect) {
        super(effect);
    }
}


class EverythingIsColorlessEffect extends ContinuousEffectImpl {
    
    public EverythingIsColorlessEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Neutral);
        staticText = "All cards that aren't on the battlefield, spells, and permanents are colorless";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ObjectColor colorless = new ObjectColor();
            // permaments
            for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                perm.getColor(game).setColor(colorless);
            }
            // spells
            for (MageObject object : game.getStack()) {
                if (object instanceof Spell) {
                    object.getColor(game).setColor(colorless);
                }
            }
            // exile
            for (Card card : game.getExile().getAllCards(game)) {
                game.getState().getCreateCardAttribute(card).getColor().setColor(colorless);
            }
            // command
            for (CommandObject commandObject : game.getState().getCommand()) {
                commandObject.getColor(game).setColor(colorless);
            }
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    // hand
                    for (Card card: player.getHand().getCards(game)) {
                        game.getState().getCreateCardAttribute(card).getColor().setColor(colorless);
                    }
                    // library
                    for (Card card : player.getLibrary().getCards(game)) {
                        game.getState().getCreateCardAttribute(card).getColor().setColor(colorless);
                    }
                    // graveyard
                    for (Card card : player.getGraveyard().getCards(game)) {
                        game.getState().getCreateCardAttribute(card).getColor().setColor(colorless);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public EverythingIsColorlessEffect copy() {
        return new EverythingIsColorlessEffect(this);
    }

    private EverythingIsColorlessEffect(EverythingIsColorlessEffect effect) {
        super(effect);
    }
}


class ManaCanBeSpentAsAnyColorEffect extends AsThoughEffectImpl {

    public ManaCanBeSpentAsAnyColorEffect() {
        super(AsThoughEffectType.SPEND_ANY_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Players may spend mana as though it were mana of any color";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return true;
    }

    @Override
    public ManaCanBeSpentAsAnyColorEffect copy() {
        return new ManaCanBeSpentAsAnyColorEffect(this);
    }

    private ManaCanBeSpentAsAnyColorEffect(ManaCanBeSpentAsAnyColorEffect effect) {
        super(effect);
    }    
}
