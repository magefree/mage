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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class MythRealized extends CardImpl {
    
    private static final FilterSpell filterNonCreature = new FilterSpell("a noncreature spell");

    static {
        filterNonCreature.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }
    
    public MythRealized(UUID ownerId) {
        super(ownerId, 26, "Myth Realized", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.expansionSetCode = "DTK";

        // Whenever you cast a noncreature spell, put a lore counter on Myth Realized.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.LORE.createInstance()), filterNonCreature, false));

        // 2W: Put a lore counter on Myth Realized.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.LORE.createInstance()), new ManaCostsImpl("{2}{W}")));

        // W: Until end of turn, Myth Realized becomes a Monk Avatar creature in addition to its other types and gains "This creature's power and toughness are each equal to the number of lore counters on it."
        Effect effect = new BecomesCreatureSourceEffect(new MythRealizedToken(), null, Duration.EndOfTurn);
        effect.setText("Until end of turn, {this} becomes a Monk Avatar creature in addition to its other types");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{W}"));
        ability.addEffect(new MythRealizedSetPTEffect(Duration.EndOfTurn));
        this.addAbility(ability);        
        
    }

    public MythRealized(final MythRealized card) {
        super(card);
    }

    @Override
    public MythRealized copy() {
        return new MythRealized(this);
    }
}

class MythRealizedToken extends Token {

    public MythRealizedToken() {
        super("", "Monk Avatar creature in addition to its other types and gains \"This creature's power and toughness are each equal to the number of lore counters on it.\"");
        cardType.add(CardType.CREATURE);
        subtype.add("Monk");
        subtype.add("Avatar");
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

}

class MythRealizedSetPTEffect extends ContinuousEffectImpl {

    public MythRealizedSetPTEffect(Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = "and gains \"This creature's power and toughness are each equal to the number of lore counters on it.\"";
    }

    public MythRealizedSetPTEffect(final MythRealizedSetPTEffect effect) {
        super(effect);
    }

    @Override
    public MythRealizedSetPTEffect copy() {
        return new MythRealizedSetPTEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null && new MageObjectReference(source.getSourceObject(game)).refersTo(permanent)) {
                int amount = permanent.getCounters().getCount(CounterType.LORE);
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
