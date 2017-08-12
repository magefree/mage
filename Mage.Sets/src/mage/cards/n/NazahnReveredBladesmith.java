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
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandOrOnBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public class NazahnReveredBladesmith extends CardImpl {

    private static final FilterControlledCreaturePermanent equippedFilter = new FilterControlledCreaturePermanent("equipped creatures you control");

    static {
        equippedFilter.add(new EquippedPredicate());
        equippedFilter.add(new ControllerPredicate(TargetController.YOU));
    }

    private static final FilterCard filter = new FilterCard("Equipment card");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new SubtypePredicate(SubType.EQUIPMENT));
    }

    public NazahnReveredBladesmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Cat");
        this.subtype.add("Artificer");
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Nazahn, Revered Bladesmith enters the battlefield, search your library for an Equipment card and reveal it. If you reveal a card named Hammer of Nazahn this way, put it onto the battlefield. Otherwise, put that card into your hand. Then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(1, 1, filter);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandOrOnBattlefieldEffect(target, true, true, "Hammer of Nazahn"), true));

        // Whenever an equipped creature you control attacks, you may tap target creature defending player controls.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(new NazahnTapEffect(), false, equippedFilter, true);
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature defending player controls")));
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof AttacksCreatureYouControlTriggeredAbility) {
            FilterCreaturePermanent filterDefender = new FilterCreaturePermanent("creature defending player controls");
            for (Effect effect : ability.getEffects()) {
                if (effect instanceof NazahnTapEffect) {
                    filterDefender.add(new ControllerIdPredicate(game.getCombat().getDefendingPlayerId(effect.getTargetPointer().getFirst(game, ability), game)));
                    break;
                }
            }
            ability.getTargets().clear();
            TargetCreaturePermanent target = new TargetCreaturePermanent(filterDefender);
            ability.addTarget(target);
        }
    }

    public NazahnReveredBladesmith(final NazahnReveredBladesmith card) {
        super(card);
    }

    @Override
    public NazahnReveredBladesmith copy() {
        return new NazahnReveredBladesmith(this);
    }
}

class NazahnTapEffect extends TapTargetEffect {

    NazahnTapEffect() {
        super();
    }

    NazahnTapEffect(final NazahnTapEffect effect) {
        super(effect);
    }

    @Override
    public NazahnTapEffect copy() {
        return new NazahnTapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.tap(game);
            return true;
        }
        return false;
    }
}
