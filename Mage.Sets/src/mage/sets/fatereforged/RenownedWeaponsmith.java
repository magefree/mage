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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public class RenownedWeaponsmith extends CardImpl {

    public RenownedWeaponsmith(UUID ownerId) {
        super(ownerId, 48, "Renowned Weaponsmith", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Human");
        this.subtype.add("Artificer");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {t}: Add {2} to your mana pool. Spend this mana only to cast artifact spells or activate abilities of artifacts.
        this.addAbility(new ConditionalColorlessManaAbility(new TapSourceCost(), 2, new RenownedWeaponsmithManaBuilder()));

        // {U}, {T}: Search your library for a card named Heart-Piercer Bow or Vial of Dragonfire, reveal it, put it into your hand, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RenownedWeaponsmithEffect(), new ManaCostsImpl("{U"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public RenownedWeaponsmith(final RenownedWeaponsmith card) {
        super(card);
    }

    @Override
    public RenownedWeaponsmith copy() {
        return new RenownedWeaponsmith(this);
    }
}

class RenownedWeaponsmithManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new RenownedWeaponsmithConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast artifact spells or activate abilities of artifacts";
    }
}

class RenownedWeaponsmithConditionalMana extends ConditionalMana {

    public RenownedWeaponsmithConditionalMana(Mana mana) {
        super(mana);
        addCondition(new RenownedWeaponsmithCondition());
    }
}

class RenownedWeaponsmithCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        return (object != null
                && object.getCardType().contains(CardType.ARTIFACT));
    }
}

class RenownedWeaponsmithEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card named Heart-Piercer Vial of Dragonfire");

    static {
        filter.add(Predicates.or(new NamePredicate("Heart-Piercer Bow"),
                new NamePredicate("Vial of Dragonfire")));
    }

    public RenownedWeaponsmithEffect() {
        super(Outcome.DrawCard);
        staticText = "Search your library for a card named Heart-Piercer Bow or Vial of Dragonfire, reveal it, put it into your hand, then shuffle your library";
    }

    public RenownedWeaponsmithEffect(final RenownedWeaponsmithEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(target, game)) {
                if (target.getTargets().size() > 0) {
                    Card card = game.getCard(target.getFirstTarget());
                    Cards revealed = new CardsImpl();
                    revealed.add(card);
                    controller.revealCards(sourceObject.getIdName(), revealed, game);
                    controller.moveCards(revealed, null, Zone.HAND, source, game);
                }
            }
            controller.shuffleLibrary(game);
            return true;
        }
        return false;
    }

    @Override
    public RenownedWeaponsmithEffect copy() {
        return new RenownedWeaponsmithEffect(this);
    }
}
