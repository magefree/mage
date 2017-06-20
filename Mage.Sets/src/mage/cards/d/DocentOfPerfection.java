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

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.f.FinalIteration;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.token.HumanWizardToken;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class DocentOfPerfection extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("an instant or sorcery spell");

    static {
        filterSpell.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public DocentOfPerfection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add("Insect");
        this.subtype.add("Horror");
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        this.transformable = true;
        this.secondSideCardClazz = FinalIteration.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, create a 1/1 blue Human Wizard creature token.
        // Then if you control three or more Wizards, transform Docent of Perfection.
        this.addAbility(new TransformAbility());
        Effect effect = new DocentOfPerfectionEffect();
        Ability ability = new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new HumanWizardToken()), filterSpell, false);
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public DocentOfPerfection(final DocentOfPerfection card) {
        super(card);
    }

    @Override
    public DocentOfPerfection copy() {
        return new DocentOfPerfection(this);
    }
}

class DocentOfPerfectionEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("Wizards");

    static {
        filter.add(new SubtypePredicate(SubType.WIZARD));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public DocentOfPerfectionEffect() {
        super(Outcome.Benefit);
        staticText = "Then if you control three or more Wizards, transform {this}";
    }

    public DocentOfPerfectionEffect(final DocentOfPerfectionEffect effect) {
        super(effect);
    }

    @Override
    public DocentOfPerfectionEffect copy() {
        return new DocentOfPerfectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) >= 3) {
                return new TransformSourceEffect(true).apply(game, source);
            }
        }
        return false;
    }
}

