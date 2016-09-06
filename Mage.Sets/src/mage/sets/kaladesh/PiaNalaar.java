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
package mage.sets.kaladesh;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Wehk
 */
public class PiaNalaar extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("artifact creature");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public PiaNalaar(UUID ownerId) {
        super(ownerId, 124, "Pia Nalaar", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "KLD";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Artificer");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Pia Nalaar enters the battlefield, create a 1/1 colorless Thopter artifact creature token with flying.
        Effect effect = new CreateTokenEffect(new ThopterColorlessToken(), 1);
        effect.setText("create a 1/1 colorless Thopter artifact creature token with flying");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));

        // {1}{R}: Target artifact creature gets +1/+0 until end of turn.        
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl("{1}{R}"));
        Target target = new TargetPermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);
        
        // {1}, Sacrifice an artifact: Target creature can't block this turn.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBlockTargetEffect(Duration.EndOfTurn), new ManaCostsImpl("{1}"));
        ability2.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, new FilterControlledArtifactPermanent("an artifact"), true)));
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
    }

    public PiaNalaar(final PiaNalaar card) {
        super(card);
    }

    @Override
    public PiaNalaar copy() {
        return new PiaNalaar(this);
    }
}
