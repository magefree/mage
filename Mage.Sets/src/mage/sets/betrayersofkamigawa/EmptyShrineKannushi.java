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
package mage.sets.betrayersofkamigawa;

import java.util.ArrayList;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.FilterObject;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class EmptyShrineKannushi extends CardImpl<EmptyShrineKannushi> {

    public EmptyShrineKannushi(UUID ownerId) {
        super(ownerId, 2, "Empty-Shrine Kannushi", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Empty-Shrine Kannushi has protection from the colors of permanents you control.
        this.addAbility(new EmptyShrineKannushiProtectionAbility());
    }

    public EmptyShrineKannushi(final EmptyShrineKannushi card) {
        super(card);
    }

    @Override
    public EmptyShrineKannushi copy() {
        return new EmptyShrineKannushi(this);
    }
}

class EmptyShrineKannushiProtectionAbility extends ProtectionAbility {
 
    public EmptyShrineKannushiProtectionAbility() {
        super(new FilterCard());
    }

    public EmptyShrineKannushiProtectionAbility(final EmptyShrineKannushiProtectionAbility ability) {
        super(ability);
    }

    @Override
    public EmptyShrineKannushiProtectionAbility copy() {
        return new EmptyShrineKannushiProtectionAbility(this);
    }
    
    @Override
    public boolean canTarget(MageObject source, Game game) {
        ObjectColor color = new ObjectColor();
        for (Permanent permanent: game.getBattlefield().getAllActivePermanents(controllerId)) {
            ObjectColor permanentColor = permanent.getColor();
            if (permanentColor.isColorless()) {
                continue;
            }
            if (permanentColor.isBlack()) {
                color.setBlack(true);
            }
            if (permanentColor.isBlue()) {
                color.setBlue(true);
            }
            if (permanentColor.isGreen()) {
                color.setGreen(true);
            }
            if (permanentColor.isRed()) {
                color.setRed(true);
            }
            if (permanentColor.isWhite()) {
                color.setWhite(true);
            }
        }

        ArrayList<Predicate<MageObject>> colorPredicates = new ArrayList<Predicate<MageObject>>();
        if (color.isBlack()) {
            colorPredicates.add(new ColorPredicate(ObjectColor.BLACK));
        }
        if (color.isBlue()) {
            colorPredicates.add(new ColorPredicate(ObjectColor.BLUE));
        }
        if (color.isGreen()) {
            colorPredicates.add(new ColorPredicate(ObjectColor.GREEN));
        }
        if (color.isRed()) {
            colorPredicates.add(new ColorPredicate(ObjectColor.RED));
        }
        if (color.isWhite()) {
            colorPredicates.add(new ColorPredicate(ObjectColor.WHITE));
        }
        Filter protectionFilter = new FilterObject("the colors of permanents you control");
        protectionFilter.add(Predicates.or(colorPredicates));
        this.filter = protectionFilter;
        return super.canTarget(source, game);
    }

    @Override
    public String getRule() {
        return "Empty-Shrine Kannushi has protection from the colors of permanents you control.";
    }
}

