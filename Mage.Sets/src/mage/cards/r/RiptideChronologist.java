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
package mage.cards.r;

import java.util.UUID;
import java.util.stream.Collectors;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author fireshoes
 */
public class RiptideChronologist extends CardImpl {

    public RiptideChronologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {U}, Sacrifice Riptide Chronologist: Untap all creatures of the creature type of your choice.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RiptideChronologistEffect(), new ManaCostsImpl("{U}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public RiptideChronologist(final RiptideChronologist card) {
        super(card);
    }

    @Override
    public RiptideChronologist copy() {
        return new RiptideChronologist(this);
    }
}

class RiptideChronologistEffect extends OneShotEffect {

    public RiptideChronologistEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Untap all creatures of the creature type of your choice";
    }

    public RiptideChronologistEffect(final RiptideChronologistEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null) {
            Choice typeChoice = new ChoiceImpl(true);
            typeChoice.setMessage("Choose a creature type:");
            typeChoice.setChoices(SubType.getCreatureTypes(false).stream().map(SubType::toString).collect(Collectors.toSet()));
            while (!player.choose(outcome, typeChoice, game)) {
                if (!player.canRespond()) {
                    return false;
                }
            }
            if (typeChoice.getChoice() != null) {
                game.informPlayers(sourceObject.getLogName() + " chosen type: " + typeChoice.getChoice());
            }
            FilterCreaturePermanent filterCreaturePermanent = new FilterCreaturePermanent();
            filterCreaturePermanent.add(new SubtypePredicate(SubType.byDescription(typeChoice.getChoice())));
            for (Permanent creature : game.getBattlefield().getActivePermanents(filterCreaturePermanent, source.getSourceId(), game)) {
                creature.untap(game);
            }
            return true;
        }
        return false;
    }

    @Override
    public RiptideChronologistEffect copy() {
        return new RiptideChronologistEffect(this);
    }
}
