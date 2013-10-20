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
package mage.sets.legions;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author cbt33, Plopman (Engineered Plague)
 */
public class MistformSliver extends CardImpl<MistformSliver> {

    public MistformSliver(UUID ownerId) {
        super(ownerId, 46, "Mistform Sliver", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "LGN";
        this.subtype.add("Illusion");
        this.subtype.add("Sliver");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Slivers have "{1}: This permanent becomes the creature type of your choice in addition to its other types until end of turn."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MistformSliverEffect(), new ManaCostsImpl("{1}"));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(ability, Duration.WhileOnBattlefield, new FilterCreaturePermanent("Sliver", "Sliver creatures"))));
    }

    public MistformSliver(final MistformSliver card) {
        super(card);
    }

    @Override
    public MistformSliver copy() {
        return new MistformSliver(this);
    }
}

 class MistformSliverEffect extends OneShotEffect<MistformSliverEffect> {

        public MistformSliverEffect() {
            super(Outcome.Benefit);
            staticText = "This permanent becomes the creature type of your choice in addition to its other types until end of turn";
        }

        public MistformSliverEffect(final MistformSliverEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (player != null && permanent != null) {
                Choice typeChoice = new ChoiceImpl(true);
                typeChoice.setMessage("Choose creature type");
                typeChoice.setChoices(CardRepository.instance.getCreatureTypes());
                while (!player.choose(Outcome.Detriment, typeChoice, game)) {
                    if (!player.isInGame()) {
                        return false;
                    }
                }
                game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + typeChoice.getChoice());
                game.getState().setValue(permanent.getId() + "_type", typeChoice.getChoice().toString());
                permanent.addInfo("chosen type", "<i>Chosen type: " + typeChoice.getChoice() + "</i>");
                permanent.getSubtype().add(typeChoice.getChoice());
            }
            return false;
        }

        @Override
        public MistformSliverEffect copy() {
            return new MistformSliverEffect(this);
        }

    }
