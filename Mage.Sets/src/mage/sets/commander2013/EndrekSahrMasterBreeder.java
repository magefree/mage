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
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;

/**
 *
 * @author LevelX2
 */
public class EndrekSahrMasterBreeder extends CardImpl<EndrekSahrMasterBreeder> {

    private static final FilterSpell filter = new FilterSpell("a creature spell");
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public EndrekSahrMasterBreeder(UUID ownerId) {
        super(ownerId, 76, "Endrek Sahr, Master Breeder", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "C13";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a creature spell, put X 1/1 black Thrull creature tokens onto the battlefield, where X is that spell's converted mana cost.
        this.addAbility(new SpellCastControllerTriggeredAbility(new EndrekSahrMasterBreederEffect(), filter, false, true));
        // When you control seven or more Thrulls, sacrifice Endrek Sahr, Master Breeder.
        this.addAbility(new EndrekSahrMasterBreederTriggeredAbility());
    }

    public EndrekSahrMasterBreeder(final EndrekSahrMasterBreeder card) {
        super(card);
    }

    @Override
    public EndrekSahrMasterBreeder copy() {
        return new EndrekSahrMasterBreeder(this);
    }
}

class EndrekSahrMasterBreederTriggeredAbility extends StateTriggeredAbility<EndrekSahrMasterBreederTriggeredAbility> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(new SubtypePredicate("Thrull"));
    }

    public EndrekSahrMasterBreederTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public EndrekSahrMasterBreederTriggeredAbility(final EndrekSahrMasterBreederTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EndrekSahrMasterBreederTriggeredAbility copy() {
        return new EndrekSahrMasterBreederTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return (game.getBattlefield().countAll(filter, this.getControllerId(), game) >= 7);
    }

    @Override
    public String getRule() {
        return "When you control seven or more Thrulls, sacrifice {this}.";
    }
}

class EndrekSahrMasterBreederEffect extends OneShotEffect<EndrekSahrMasterBreederEffect> {

    public EndrekSahrMasterBreederEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put X 1/1 black Thrull creature tokens onto the battlefield, where X is that spell's converted mana cost";
    }

    public EndrekSahrMasterBreederEffect(final EndrekSahrMasterBreederEffect effect) {
        super(effect);
    }

    @Override
    public EndrekSahrMasterBreederEffect copy() {
        return new EndrekSahrMasterBreederEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            int cmc = spell.getManaCost().convertedManaCost();
            if (cmc > 0) {
                return new CreateTokenEffect(new EndrekSahrMasterBreederThrullToken(), cmc).apply(game, source);
            }
            return true;
        }
        return false;
    }
}

class EndrekSahrMasterBreederThrullToken extends Token {

    public EndrekSahrMasterBreederThrullToken() {
        super("Thrull", "1/1 black Thrull creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Thrull");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
