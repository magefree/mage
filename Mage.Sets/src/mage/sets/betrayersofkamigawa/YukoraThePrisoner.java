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
package mage.sets.betrayersofkamigawa;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class YukoraThePrisoner extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Ogre creatures");

    static {
        filter.add(Predicates.not(new SubtypePredicate("Ogre")));
    }

    public YukoraThePrisoner(UUID ownerId) {
        super(ownerId, 90, "Yukora, the Prisoner", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "BOK";
        this.supertype.add("Legendary");
        this.subtype.add("Demon");
        this.subtype.add("Spirit");

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Yukora, the Prisoner leaves the battlefield, sacrifice all non-Ogre creatures you control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new YukoraThePrisonerEffect(), false));

    }

    public YukoraThePrisoner(final YukoraThePrisoner card) {
        super(card);
    }

    @Override
    public YukoraThePrisoner copy() {
        return new YukoraThePrisoner(this);
    }
}

class YukoraThePrisonerEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Ogre creatures");

    static {
        filter.add(Predicates.not(new SubtypePredicate("Ogre")));
    }

    public YukoraThePrisonerEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "sacrifice all non-Ogre creatures you control";
    }

    public YukoraThePrisonerEffect(final YukoraThePrisonerEffect effect) {
        super(effect);
    }

    @Override
    public YukoraThePrisonerEffect copy() {
        return new YukoraThePrisonerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            permanent.sacrifice(source.getSourceId(), game);
        }
        return true;
    }
}
