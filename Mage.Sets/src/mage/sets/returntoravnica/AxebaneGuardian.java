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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class AxebaneGuardian extends CardImpl<AxebaneGuardian> {

    public AxebaneGuardian(UUID ownerId) {
        super(ownerId, 115, "Axebane Guardian", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Human");
        this.subtype.add("Druid");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // {tap}: Add X mana in any combination of colors to your mana pool, where X is the number of creatures with defender you control.
        this.addAbility(new SimpleManaAbility(Constants.Zone.BATTLEFIELD, new AxebaneGuardianManaEffect(), new TapSourceCost()));
    }

    public AxebaneGuardian(final AxebaneGuardian card) {
        super(card);
    }

    @Override
    public AxebaneGuardian copy() {
        return new AxebaneGuardian(this);
    }
}


class AxebaneGuardianManaEffect extends ManaEffect<AxebaneGuardianManaEffect> {


    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creatures with defender you control");
    static{
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }
    public AxebaneGuardianManaEffect() {
        super();
        this.staticText = "Add X mana in any combination of colors to your mana pool, where X is the number of creatures with defender you control";
    }

    public AxebaneGuardianManaEffect(final AxebaneGuardianManaEffect effect) {
        super(effect);
    }

    @Override
    public AxebaneGuardianManaEffect copy() {
        return new AxebaneGuardianManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null){
                int x = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);

            Mana mana = new Mana();
            for(int i = 0; i < x; i++){
                ChoiceColor choiceColor = new ChoiceColor();
                while (!player.choose(Constants.Outcome.Benefit, choiceColor, game)) {
                    game.debugMessage("player canceled choosing color. retrying.");
                }
                
                if (choiceColor.getColor().isBlack()) {
                    mana.addBlack();
                } else if (choiceColor.getColor().isBlue()) {
                    mana.addBlue();
                } else if (choiceColor.getColor().isRed()) {
                    mana.addRed();
                } else if (choiceColor.getColor().isGreen()) {
                    mana.addGreen();
                } else if (choiceColor.getColor().isWhite()) {
                    mana.addWhite();
                }
            }


            if (player != null && mana != null) {
                player.getManaPool().addMana(mana, game, source);
                return true;
            }

        }
        return false;
    }
}
