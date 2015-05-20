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
package mage.sets.guildpact;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class TeysaOrzhovScion extends CardImpl {
    
    private final static FilterControlledCreaturePermanent filterWhite = new FilterControlledCreaturePermanent("three white creatures");
    private final static FilterCreaturePermanent filterBlack = new FilterCreaturePermanent("another black creature you control");
    static {
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterBlack.add(new AnotherPredicate());
        filterBlack.add(new ControllerPredicate(TargetController.YOU));
    }

    public TeysaOrzhovScion(UUID ownerId) {
        super(ownerId, 134, "Teysa, Orzhov Scion", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");
        this.expansionSetCode = "GPT";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Advisor");

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Sacrifice three white creatures: Exile target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new SacrificeTargetCost(new TargetControlledCreaturePermanent(3, 3, filterWhite, true)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // Whenever another black creature you control dies, put a 1/1 white Spirit creature token with flying onto the battlefield.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken("GPT")), false, filterBlack));
    }

    public TeysaOrzhovScion(final TeysaOrzhovScion card) {
        super(card);
    }

    @Override
    public TeysaOrzhovScion copy() {
        return new TeysaOrzhovScion(this);
    }
}
