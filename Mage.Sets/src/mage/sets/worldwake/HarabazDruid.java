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
package mage.sets.worldwake;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Loki
 */
public class HarabazDruid extends CardImpl<HarabazDruid> {

    public HarabazDruid(UUID ownerId) {
        super(ownerId, 105, "Harabaz Druid", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Human");
        this.subtype.add("Druid");
        this.subtype.add("Ally");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {tap}: Add X mana of any one color to your mana pool, where X is the number of Allies you control.
        this.addAbility(new HarabazDruidManaAbility());
    }

    public HarabazDruid(final HarabazDruid card) {
        super(card);
    }

    @Override
    public HarabazDruid copy() {
        return new HarabazDruid(this);
    }
}

class HarabazDruidManaAbility extends BasicManaAbility<HarabazDruidManaAbility> {
    HarabazDruidManaAbility() {
        super(new HarabazDruidManaEffect());
        this.addChoice(new ChoiceColor());
    }

    HarabazDruidManaAbility(final HarabazDruidManaAbility ability) {
        super(ability);
    }

    @Override
    public HarabazDruidManaAbility copy() {
        return new HarabazDruidManaAbility(this);
    }
}

class HarabazDruidManaEffect extends ManaEffect<HarabazDruidManaEffect> {
    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new SubtypePredicate("Ally"));
    }

    HarabazDruidManaEffect() {
        super();
        staticText = "Add X mana of any one color to your mana pool, where X is the number of Allies you control";
    }

    HarabazDruidManaEffect(final HarabazDruidManaEffect effect) {
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
    public HarabazDruidManaEffect copy() {
        return new HarabazDruidManaEffect(this);
    }
}