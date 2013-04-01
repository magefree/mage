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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continious.PlayWithTheTopCardRevealedEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class SkillBorrower extends CardImpl<SkillBorrower> {

    public SkillBorrower(UUID ownerId) {
        super(ownerId, 56, "Skill Borrower", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect()));
        // As long as the top card of your library is an artifact or creature card, Skill Borrower has all activated abilities of that card.
        this.addAbility(new SkillBorrowerAbility());
    }

    public SkillBorrower(final SkillBorrower card) {
        super(card);
    }

    @Override
    public SkillBorrower copy() {
        return new SkillBorrower(this);
    }
}


class SkillBorrowerAbility extends StaticAbility<SkillBorrowerAbility> {

    public SkillBorrowerAbility() {
        super(Zone.BATTLEFIELD, new SkillBorrowerEffect());
    }

    public SkillBorrowerAbility(SkillBorrowerAbility ability) {
        super(ability);
    }

    @Override
    public SkillBorrowerAbility copy() {
        return new SkillBorrowerAbility(this);
    }

    @Override
    public String getRule() {
        return "As long as the top card of your library is an artifact or creature card, Skill Borrower has all activated abilities of that card";
    }
}

class SkillBorrowerEffect extends ContinuousEffectImpl<SkillBorrowerEffect> {

    public SkillBorrowerEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Layer.AbilityAddingRemovingEffects_6, Constants.SubLayer.NA, Constants.Outcome.AddAbility);
        staticText = "As long as the top card of your library is an artifact or creature card, Skill Borrower has all activated abilities of that card";
    }

    public SkillBorrowerEffect(final SkillBorrowerEffect effect) {
        super(effect);
    }


    @Override
    public SkillBorrowerEffect copy() {
        return new SkillBorrowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null){
            Card card = player.getLibrary().getFromTop(game);
            if(card != null && (card.getCardType().contains(CardType.CREATURE) || card.getCardType().contains(CardType.ARTIFACT))){
                Permanent permanent = game.getPermanent(source.getSourceId());
                if(permanent != null){
                    for(Ability ability : card.getAbilities()){
                        if(ability instanceof ActivatedAbility){
                            permanent.addAbility(ability, source.getId(), game);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

}
