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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageObject;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityControllerEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.FilterObject;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class RunedHalo extends CardImpl<RunedHalo> {

    public RunedHalo(UUID ownerId) {
        super(ownerId, 21, "Runed Halo", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{W}{W}");
        this.expansionSetCode = "SHM";

        this.color.setWhite(true);

        // As Runed Halo enters the battlefield, name a card.
        this.addAbility(new AsEntersBattlefieldAbility(new NameCard()));

        /**
         * 5/1/2008: Runed Halo gives you protection from each object with the chosen name, whether it's a card, a token, or a copy of a spell. It doesn't matter what game zone that object is in.
         * 5/1/2008: You can still be attacked by creatures with the chosen name.
         * 5/1/2008: You'll have protection from the name, not from the word. For example, if you choose the name Forest, you'll have protection from anything named "Forest" -- but you won't have protection from Forests. An animated Sapseep Forest, for example, could deal damage to you even though its subtype is Forest.
         * 5/1/2008: You can name either half of a split card, but not both. You'll have protection from the half you named (and from a fused split spell with that name), but not the other half.
         * 5/1/2008: You can't choose [nothing] as a name. Face-down creatures have no name, so Runed Halo can't give you protection from them.
         * 5/1/2008: You must choose the name of a card, not the name of a token. For example, you can't choose "Saproling" or "Voja." However, if a token happens to have the same name as a card (such as "Shapeshifter" or "Goldmeadow Harrier"), you can choose it.
         * 5/1/2008: You may choose either one of a flip card's names. You'll have protection only from the appropriate version. For example, if you choose Nighteyes the Desecrator, you won't have protection from Nezumi Graverobber.
         */
        // You have protection from the chosen name.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RunedHaloRuleTextEffect()));

    }

    public RunedHalo(final RunedHalo card) {
        super(card);
    }

    @Override
    public RunedHalo copy() {
        return new RunedHalo(this);
    }
}

class NameCard extends OneShotEffect<NameCard> {

    public NameCard() {
        super(Outcome.Detriment);
        staticText = "name a card";
    }

    public NameCard(final NameCard effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNames());
            cardChoice.clearChoice();
            while (!controller.choose(Outcome.Detriment, cardChoice, game)) {
                if (!controller.isInGame()) {
                    return false;
                }
            }
            String cardName = cardChoice.getChoice();
            game.informPlayers(new StringBuilder(sourcePermanent.getName()).append(", named card: [").append(cardName).append("]").toString());
            sourcePermanent.addInfo("Named card", new StringBuilder("[Named card: ").append(cardName).append("]").toString());

            FilterObject filter = new FilterObject("the name [" + cardName + "]");
            filter.add(new NamePredicate(cardName));            
            ContinuousEffect effect = new GainAbilityControllerEffect(new ProtectionAbility(filter), Duration.Custom);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }

    @Override
    public NameCard copy() {
        return new NameCard(this);
    }

}

class RunedHaloRuleTextEffect extends OneShotEffect<RunedHaloRuleTextEffect> {
    
    public RunedHaloRuleTextEffect() {
        super(Outcome.Benefit);
        this.staticText = "You have protection from the chosen name  <i>(You can't be targeted, dealt damage, or enchanted by anything with that name.)<i/>";
    }
    
    public RunedHaloRuleTextEffect(final RunedHaloRuleTextEffect effect) {
        super(effect);
    }
    
    @Override
    public RunedHaloRuleTextEffect copy() {
        return new RunedHaloRuleTextEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
