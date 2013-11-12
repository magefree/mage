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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class SekKuarDeathkeeper extends CardImpl<SekKuarDeathkeeper> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another nontoken creature you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public SekKuarDeathkeeper(UUID ownerId) {
        super(ownerId, 210, "Sek'Kuar, Deathkeeper", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{R}{G}");
        this.expansionSetCode = "C13";
        this.supertype.add("Legendary");
        this.subtype.add("Orc");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever another nontoken creature you control dies, put a 3/1 black and red Graveborn creature token with haste onto the battlefield.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new SekKuarDeathkeeperGravebornToken()),false, filter));
    }

    public SekKuarDeathkeeper(final SekKuarDeathkeeper card) {
        super(card);
    }

    @Override
    public SekKuarDeathkeeper copy() {
        return new SekKuarDeathkeeper(this);
    }
}

class SekKuarDeathkeeperGravebornToken extends Token {

    public SekKuarDeathkeeperGravebornToken() {
        super("Graveborn", "3/1 black and red Graveborn creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setRed(true);
        subtype.add("Graveborn");
        power = new MageInt(3);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
    }
}
