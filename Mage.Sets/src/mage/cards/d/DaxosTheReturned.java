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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class DaxosTheReturned extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an enchantment spell");

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public DaxosTheReturned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{B}");
        this.supertype.add("Legendary");
        this.subtype.add("Zombie");
        this.subtype.add("Soldier");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an enchantment spell, you get an experience counter.
        Effect effect = new AddCountersControllerEffect(CounterType.EXPERIENCE.createInstance(1), false);
        effect.setText("you get an experience counter");
        Ability ability = new SpellCastControllerTriggeredAbility(effect, filter, false);
        this.addAbility(ability);

        // {1}{W}{B}: Put a white and black Spirit enchantment creature token onto the battlefield. It has
        // "This creature's power and toughness are each equal to the number of experience counters you have."
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new DaxosSpiritToken(), 1), new ManaCostsImpl("{1}{W}{B}")));
    }

    public DaxosTheReturned(final DaxosTheReturned card) {
        super(card);
    }

    @Override
    public DaxosTheReturned copy() {
        return new DaxosTheReturned(this);
    }
}

class DaxosSpiritToken extends Token {

    DaxosSpiritToken() {
        super("Spirit", "white and black Spirit enchantment creature token with \"This creature's power and toughness are each equal to the number of experience counters you have.\"");
        this.setOriginalExpansionSetCode("C15");
        setTokenType(2);
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add("Spirit");
        power = new MageInt(0);
        toughness = new MageInt(0);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DaxosSpiritSetPTEffect()));
    }
}

class DaxosSpiritSetPTEffect extends ContinuousEffectImpl {

    public DaxosSpiritSetPTEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = "This creature's power and toughness are each equal to the number of experience counters you have";
    }

    public DaxosSpiritSetPTEffect(final DaxosSpiritSetPTEffect effect) {
        super(effect);
    }

    @Override
    public DaxosSpiritSetPTEffect copy() {
        return new DaxosSpiritSetPTEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null && new MageObjectReference(source.getSourceObject(game), game).refersTo(permanent, game)) {
                int amount = controller.getCounters().getCount(CounterType.EXPERIENCE);
                permanent.getPower().setValue(amount);
                permanent.getToughness().setValue(amount);
                return true;
            } else {
                discard();
            }
        }
        return false;
    }
}
