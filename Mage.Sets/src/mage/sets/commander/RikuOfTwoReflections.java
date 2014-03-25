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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.game.permanent.token.EmptyToken;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class RikuOfTwoReflections extends CardImpl<RikuOfTwoReflections> {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell");
    private static final FilterControlledCreaturePermanent filterPermanent = new FilterControlledCreaturePermanent("another nontoken creature");
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
        filterPermanent.add(Predicates.not(new TokenPredicate()));
        filterPermanent.add(new AnotherPredicate());

    }

    public RikuOfTwoReflections(UUID ownerId) {
        super(ownerId, 220, "Riku of Two Reflections", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{U}{R}{G}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an instant or sorcery spell, you may pay {U}{R}. If you do, copy that spell. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(new RikuOfTwoReflectionsCopyEffect(), new ManaCostsImpl("{U}{R}")), filter, false, true));

        // Whenever another nontoken creature enters the battlefield under your control, you may pay {G}{U}. If you do, put a token that's a copy of that creature onto the battlefield.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new RikuOfTwoReflectionsCopyTokenEffect(),filterPermanent, false, true,
                "Whenever another nontoken creature enters the battlefield under your control, you may pay {G}{U}. If you do, put a token that's a copy of that creature onto the battlefield.",
                true);
        ability.addCost(new ManaCostsImpl("{G}{U}"));
        this.addAbility(ability);
    }

    public RikuOfTwoReflections(final RikuOfTwoReflections card) {
        super(card);
    }

    @Override
    public RikuOfTwoReflections copy() {
        return new RikuOfTwoReflections(this);
    }
}

class RikuOfTwoReflectionsCopyEffect extends OneShotEffect<RikuOfTwoReflectionsCopyEffect> {

    public RikuOfTwoReflectionsCopyEffect() {
        super(Outcome.Copy);
        staticText = "copy that spell. You may choose new targets for the copy";
    }

    public RikuOfTwoReflectionsCopyEffect(final RikuOfTwoReflectionsCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            Spell copy = spell.copySpell();
            copy.setControllerId(source.getControllerId());
            copy.setCopiedSpell(true);
            game.getStack().push(copy);
            copy.chooseNewTargets(game, source.getControllerId());
            Player player = game.getPlayer(source.getControllerId());
            String activateMessage = copy.getActivatedMessage(game);
            if (activateMessage.startsWith(" casts ")) {
                activateMessage = activateMessage.substring(6);
            }
            game.informPlayers(player.getName() + " copies " + activateMessage);
            return true;
        }
        return false;
    }

    @Override
    public RikuOfTwoReflectionsCopyEffect copy() {
        return new RikuOfTwoReflectionsCopyEffect(this);
    }
}

class RikuOfTwoReflectionsCopyTokenEffect extends OneShotEffect<RikuOfTwoReflectionsCopyTokenEffect> {

    public RikuOfTwoReflectionsCopyTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put a token that's a copy of that creature onto the battlefield";
    }

    public RikuOfTwoReflectionsCopyTokenEffect(final RikuOfTwoReflectionsCopyTokenEffect effect) {
        super(effect);
    }

    @Override
    public RikuOfTwoReflectionsCopyTokenEffect copy() {
        return new RikuOfTwoReflectionsCopyTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }

        if (permanent != null) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(permanent);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            return true;
        }

        return false;
    }
}
