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
package mage.sets.tenthedition;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.other.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public class AcademyResearchers extends CardImpl {

    public AcademyResearchers(UUID ownerId) {
        super(ownerId, 63, "Academy Researchers", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "10E";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Academy Researchers enters the battlefield, you may put an Aura card from your hand onto the battlefield attached to Academy Researchers.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AcademyResearchersEffect(), true));
    }

    public AcademyResearchers(final AcademyResearchers card) {
        super(card);
    }

    @Override
    public AcademyResearchers copy() {
        return new AcademyResearchers(this);
    }
}

class AcademyResearchersEffect extends OneShotEffect {
    
    AcademyResearchersEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may put an Aura card from your hand onto the battlefield attached to {this}.";
    }
    
    AcademyResearchersEffect(final AcademyResearchersEffect effect) {
        super(effect);
    }
    
    @Override
    public AcademyResearchersEffect copy() {
        return new AcademyResearchersEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null){
            FilterCard filter = new FilterCard();
            filter.add(new SubtypePredicate("Aura"));
            filter.add(new AuraCardCanAttachToPermanentId(permanent.getId()));
            TargetCardInHand target = new TargetCardInHand(0, 1, filter);
            if (player.choose(Outcome.PutCardInPlay, target, source.getSourceId(), game)) {
                Card auraInHand = game.getCard(target.getFirstTarget());
                if (auraInHand != null) {
                    game.getState().setValue("attachTo:" + auraInHand.getId(), permanent);
                    if (player.putOntoBattlefieldWithInfo(auraInHand, game, Zone.HAND, source.getSourceId())) {
                        permanent.addAttachment(auraInHand.getId(), game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
