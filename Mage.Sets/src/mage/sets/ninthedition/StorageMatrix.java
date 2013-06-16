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
package mage.sets.ninthedition;

import java.util.HashSet;
import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class StorageMatrix extends CardImpl<StorageMatrix> {

    public StorageMatrix(UUID ownerId) {
        super(ownerId, 310, "Storage Matrix", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "9ED";

        // As long as Storage Matrix is untapped, each player chooses artifact, creature, or land during his or her untap step. That player can untap only permanents of the chosen type this step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new StorageMatrixRestrictionEffect()));
    }

    public StorageMatrix(final StorageMatrix card) {
        super(card);
    }

    @Override
    public StorageMatrix copy() {
        return new StorageMatrix(this);
    }
}



class StorageMatrixRestrictionEffect extends RestrictionEffect<StorageMatrixRestrictionEffect> {

    private int turn;
    private boolean applies;
    private static final HashSet<String> choice = new HashSet<String>();
    static{
        choice.add(CardType.ARTIFACT.toString());
        choice.add(CardType.CREATURE.toString());
        choice.add(CardType.LAND.toString());
    }
    private CardType type;

    public StorageMatrixRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "As long as Storage Matrix is untapped, each player chooses artifact, creature, or land during his or her untap step. That player can untap only permanents of the chosen type this step";
    }

    public StorageMatrixRestrictionEffect(final StorageMatrixRestrictionEffect effect) {
        super(effect);
        this.type = effect.type;
        this.turn = effect.turn;
        this.applies = effect.applies;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (game.getStep().getType() == PhaseStep.UNTAP) {
            if (game.getTurnNum() != turn) {
                turn = game.getTurnNum();
                applies = false;
                Permanent storageMatrix = game.getPermanent(source.getSourceId());
                if (storageMatrix != null && !storageMatrix.isTapped()) {
                    Choice choiceImpl = new ChoiceImpl();
                    choiceImpl.setMessage("Untap which kind of permanent?");
                    choiceImpl.setChoices(choice);
                    Player player = game.getPlayer(game.getActivePlayerId());
                    if(player != null){
                        while(!player.choose(outcome, choiceImpl, game)) {
                            // player has to choose
                        }
                        String choosenType = choiceImpl.getChoice();
                        game.informPlayers(new StringBuilder(storageMatrix.getName()).append(": ").append(player.getName()).append(" chose to untap ").append(choosenType).toString());

                        if(choosenType.equals(CardType.ARTIFACT.toString())){
                            type = CardType.ARTIFACT;
                        }else if(choosenType.equals(CardType.LAND.toString())){
                            type = CardType.LAND;
                        }else{
                            type = CardType.CREATURE;
                        }
                        applies = true;
                    }
                }
            }
            if (applies) {
                return !permanent.getCardType().contains(type);
            }
        }
        return false;
    }

    @Override
    public boolean canBeUntapped(Permanent permanent, Game game) {
        return false;
    }

    @Override
    public StorageMatrixRestrictionEffect copy() {
        return new StorageMatrixRestrictionEffect(this);
    }
}
