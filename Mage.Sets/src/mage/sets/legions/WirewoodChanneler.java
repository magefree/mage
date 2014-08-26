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
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class WirewoodChanneler extends CardImpl {

    public WirewoodChanneler(UUID ownerId) {
        super(ownerId, 144, "Wirewood Channeler", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "LGN";
        this.subtype.add("Elf");
        this.subtype.add("Druid");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Add X mana of any one color to your mana pool, where X is the number of Elves on the battlefield.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new WirewoodChannelerManaEffect(), new TapSourceCost());
        ability.addChoice(new ChoiceColor());
        this.addAbility(ability);
    }

    public WirewoodChanneler(final WirewoodChanneler card) {
        super(card);
    }

    @Override
    public WirewoodChanneler copy() {
        return new WirewoodChanneler(this);
    }
}


class WirewoodChannelerManaEffect extends ManaEffect {
    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new SubtypePredicate("Elf"));
    }

    WirewoodChannelerManaEffect() {
        super();
        staticText = "Add X mana of any one color to your mana pool, where X is the number of Elves on the battlefield";
    }

    WirewoodChannelerManaEffect(final WirewoodChannelerManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ChoiceColor choice = (ChoiceColor) source.getChoices().get(0);
        Player player = game.getPlayer(source.getControllerId());
        int count = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
        if (choice.getColor().isBlack()) {
            player.getManaPool().addMana(new Mana(0, 0, 0, 0, count, 0, 0), game, source);
            return true;
        } else if (choice.getColor().isBlue()) {
            player.getManaPool().addMana(new Mana(0, 0, count, 0, 0, 0, 0), game, source);
            return true;
        } else if (choice.getColor().isRed()) {
            player.getManaPool().addMana(new Mana(count, 0, 0, 0, 0, 0, 0), game, source);
            return true;
        } else if (choice.getColor().isGreen()) {
            player.getManaPool().addMana(new Mana(0, count, 0, 0, 0, 0, 0), game, source);
            return true;
        } else if (choice.getColor().isWhite()) {
            player.getManaPool().addMana(new Mana(0, 0, 0, count, 0, 0, 0), game, source);
            return true;
        }
        return false;
    }

    @Override
    public WirewoodChannelerManaEffect copy() {
        return new WirewoodChannelerManaEffect(this);
    }
}