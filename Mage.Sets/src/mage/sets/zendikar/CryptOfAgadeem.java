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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.ManaAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;

/**
 *
 * @author North
 */
public class CryptOfAgadeem extends CardImpl<CryptOfAgadeem> {

    public CryptOfAgadeem(UUID ownerId) {
        super(ownerId, 212, "Crypt of Agadeem", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ZEN";

        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new BlackManaAbility());
        this.addAbility(new CryptOfAgadeemAbility());
    }

    public CryptOfAgadeem(final CryptOfAgadeem card) {
        super(card);
    }

    @Override
    public CryptOfAgadeem copy() {
        return new CryptOfAgadeem(this);
    }
}

class CryptOfAgadeemAbility extends ManaAbility<CryptOfAgadeemAbility> {

    private static final DynamicValue amount;

    static {
        FilterCreatureCard filter = new FilterCreatureCard();
        filter.setColor(ObjectColor.BLACK);
        filter.setUseColor(true);
        amount = new CardsInControllerGraveyardCount(filter);
    }

    public CryptOfAgadeemAbility() {
        super(Zone.BATTLEFIELD, new CryptOfAgadeemAbilityEffect(amount), new TapSourceCost());
        addCost(new GenericManaCost(2));
    }

    public CryptOfAgadeemAbility(final CryptOfAgadeemAbility ability) {
        super(ability);
    }

    @Override
    public CryptOfAgadeemAbility copy() {
        return new CryptOfAgadeemAbility(this);
    }

    @Override
    public Mana getNetMana(Game game) {
        if (game == null) {
            return new Mana();
        }
        return Mana.BlackMana(amount.calculate(game, this));
    }
}

class CryptOfAgadeemAbilityEffect extends ManaEffect {

    private DynamicValue amount;

    public CryptOfAgadeemAbilityEffect(DynamicValue amount) {
        super(new Mana());
        this.amount = amount;
    }

    public CryptOfAgadeemAbilityEffect(final CryptOfAgadeemAbilityEffect effect) {
        super(effect);
        this.amount = effect.amount.clone();
    }

    @Override
    public CryptOfAgadeemAbilityEffect copy() {
        return new CryptOfAgadeemAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        this.mana.clear();
        this.mana.setBlack(amount.calculate(game, source));
        return super.apply(game, source);
    }

    @Override
    public String getText(Ability source) {
        return "Add {B} to your mana pool for each black creature card in your graveyard";
    }
}