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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityControllerEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.FilterCard;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
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

        // You have protection from the chosen name.
        Effect effect = new GainAbilityControllerEffect(new ProtectionAbility(new FilterObject("empty")));
        effect.setText("You have protection from the chosen name  <i>(You can't be targeted, dealt damage, or enchanted by anything with that name.)<i/>");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

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
        super(Constants.Outcome.Detriment);
        staticText = "name a card";
    }

    public NameCard(final NameCard effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNames());
            cardChoice.clearChoice();
            while (!controller.choose(Constants.Outcome.Detriment, cardChoice, game)) {
                game.debugMessage("player canceled choosing name. retrying.");
            }
            String cardName = cardChoice.getChoice();
            game.informPlayers("Runed Halo, named card: [" + cardName + "]");
            FilterCard filter = new FilterCard(cardName);
            filter.add(new NamePredicate(cardName));
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                for (Ability ability : permanent.getAbilities()) {
                    if (ability instanceof ProtectionAbility) {
                        ((ProtectionAbility) ability).setFilter(filter);
                    }
                }
            }
            permanent.addInfo("Named card", new StringBuilder("[Named card: ").append(cardName).append("]").toString());

        }
        return false;
    }

    @Override
    public NameCard copy() {
        return new NameCard(this);
    }

}
