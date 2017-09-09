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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class GeneralTazri extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("an Ally creature card");

    static {
        filter.add(new SubtypePredicate(SubType.ALLY));
    }

    public GeneralTazri(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When General Tazri enters the battlefield, you may search your library for an Ally creature card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true, true), true));
        // {W}{U}{B}{R}{G}: Ally creatures you control get +X/+X until end of turn, where X is the number of colors among those creatures.
        DynamicValue xValue = new GeneralTazriColorCount();
        BoostControlledEffect effect = new BoostControlledEffect(xValue, xValue, Duration.EndOfTurn, new FilterCreaturePermanent(SubType.ALLY, "Ally creatures"), false);
        effect.setLockedIn(true);
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                effect,
                new ManaCostsImpl("{W}{U}{B}{R}{G}")));

    }

    public GeneralTazri(final GeneralTazri card) {
        super(card);
    }

    @Override
    public GeneralTazri copy() {
        return new GeneralTazri(this);
    }
}

class GeneralTazriColorCount implements DynamicValue {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new SubtypePredicate((SubType.ALLY)));
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        boolean black = false;
        boolean red = false;
        boolean white = false;
        boolean green = false;
        boolean blue = false;
        for (Permanent creature : game.getBattlefield().getAllActivePermanents(filter, sourceAbility.getControllerId(), game)) {
            ObjectColor color = creature.getColor(game);
            black |= color.isBlack();
            red |= color.isRed();
            white |= color.isWhite();
            green |= color.isGreen();
            blue |= color.isBlue();
        }
        count += black ? 1 : 0;
        count += red ? 1 : 0;
        count += white ? 1 : 0;
        count += green ? 1 : 0;
        count += blue ? 1 : 0;
        return count;
    }

    @Override
    public GeneralTazriColorCount copy() {
        return new GeneralTazriColorCount();
    }

    @Override
    public String getMessage() {
        return "the number of colors among those creatures";
    }

    @Override
    public String toString() {
        return "X";
    }
}
