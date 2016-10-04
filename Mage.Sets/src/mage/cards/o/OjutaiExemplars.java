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
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class OjutaiExemplars extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("a noncreature spell");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public OjutaiExemplars(UUID ownerId) {
        super(ownerId, 27, "Ojutai Exemplars", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Human");
        this.subtype.add("Monk");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cast a noncreature spell, choose one - Tap target creature; 
        Ability ability = new SpellCastControllerTriggeredAbility(new TapTargetEffect(), filter, false);
        ability.addTarget(new TargetCreaturePermanent());
        
        // Ojutai Exemplars gain first strike and lifelink until end of turn; 
        Mode mode = new Mode();
        Effect effect = new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("{this} gains first strike");
        mode.getEffects().add(effect);
        Effect effect2 = new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect2.setText("and lifelink");
        mode.getEffects().add(effect2);
        ability.addMode(mode);
        
        // or Exile Ojutai Exemplars, then return it to the battlefield tapped under its owner's control.
        mode = new Mode();
        mode.getEffects().add(new OjutaiExemplarsEffect());
        ability.addMode(mode);
        
        this.addAbility(ability);
    }

    public OjutaiExemplars(final OjutaiExemplars card) {
        super(card);
    }

    @Override
    public OjutaiExemplars copy() {
        return new OjutaiExemplars(this);
    }
}

class OjutaiExemplarsEffect extends OneShotEffect {

    public OjutaiExemplarsEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exile {this}, then return it to the battlefield tapped under its owner's control";
    }

    public OjutaiExemplarsEffect(final OjutaiExemplarsEffect effect) {
        super(effect);
    }

    @Override
    public OjutaiExemplarsEffect copy() {
        return new OjutaiExemplarsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent ojutaiExemplars = game.getPermanent(source.getSourceId());
        if (ojutaiExemplars != null) {
            if (ojutaiExemplars.moveToExile(source.getSourceId(), "Ojutai Exemplars", source.getSourceId(), game)) {
                Card card = game.getExile().getCard(source.getSourceId(), game);
                if (card != null) {
                    return card.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, true);
                }
            }
        }
        return false;
    }
}