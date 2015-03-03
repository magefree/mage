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
package mage.sets.futuresight;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.abilityword.GrandeurAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;

/**
 *
 * @author emerald000
 */
public class BaruFistOfKrosa extends CardImpl {
    
    private static final FilterLandPermanent forestFilter = new FilterLandPermanent("Forest");
    private static final FilterCreaturePermanent greenCreatureFilter = new FilterCreaturePermanent("green creatures you control");
    static {
        forestFilter.add(new SubtypePredicate("Forest"));
        greenCreatureFilter.add(new ControllerPredicate(TargetController.YOU));
        greenCreatureFilter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public BaruFistOfKrosa(UUID ownerId) {
        super(ownerId, 142, "Baru, Fist of Krosa", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "FUT";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Druid");

        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a Forest enters the battlefield, green creatures you control get +1/+1 and gain trample until end of turn.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn, greenCreatureFilter), forestFilter, "Whenever a Forest enters the battlefield, green creatures you control get +1/+1 and gain trample until end of turn.");
        ability.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, greenCreatureFilter));
        this.addAbility(ability);        
        
        // Grandeur - Discard another card named Baru, Fist of Krosa: Put an X/X green Wurm creature token onto the battlefield, where X is the number of lands you control.
        this.addAbility(new GrandeurAbility(new BaruFistOfKrosaEffect(), "Baru, Fist of Krosa"));
    }

    public BaruFistOfKrosa(final BaruFistOfKrosa card) {
        super(card);
    }

    @Override
    public BaruFistOfKrosa copy() {
        return new BaruFistOfKrosa(this);
    }
}

class BaruFistOfKrosaEffect extends OneShotEffect {
    
    final static FilterControlledPermanent filter = new FilterControlledLandPermanent("lands you control");
    
    BaruFistOfKrosaEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put an X/X green Wurm creature token onto the battlefield, where X is the number of lands you control.";
    }
    
    BaruFistOfKrosaEffect(final BaruFistOfKrosaEffect effect) {
        super(effect);
    }
    
    @Override
    public BaruFistOfKrosaEffect copy() {
        return new BaruFistOfKrosaEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = game.getBattlefield().countAll(filter, source.getControllerId(), game);
        Token token = new BaruFistOfKrosaToken(xValue);
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        return true;
    }
}

class BaruFistOfKrosaToken extends Token {
    
    BaruFistOfKrosaToken(int xValue) {
        super("Wurm", "a X/X green Wurm creature token onto the battlefield, where X is the number of lands you control");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Wurm");
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }
}