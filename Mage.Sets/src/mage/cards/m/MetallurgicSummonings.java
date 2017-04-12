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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

/**
 *
 * @author fireshoes
 */
public class MetallurgicSummonings extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public MetallurgicSummonings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");

        // Whenever you cast an instant or sorcery spell, create an X/X colorless Construct artifact creature token, where X is that spell's converted mana cost.
        this.addAbility(new SpellCastControllerTriggeredAbility(new MetallurgicSummoningsTokenEffect(), filter, false, true));

        // {3}{U}{U}, Exile Metallurgic Summons: Return all instant and sorcery cards from your graveyard to your hand. Activate this ability only if you control six or more artifacts.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new MetallurgicSummoningsReturnEffect(), new ManaCostsImpl("{3}{U}{U}"),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledArtifactPermanent(), ComparisonType.MORE_THAN, 5),
                "{3}{U}{U}, Exile {this}: Return all instant and sorcery cards from your graveyard to your hand."
                        + " Activate this ability only if you control six or more artifacts.");
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    public MetallurgicSummonings(final MetallurgicSummonings card) {
        super(card);
    }

    @Override
    public MetallurgicSummonings copy() {
        return new MetallurgicSummonings(this);
    }
}

class MetallurgicSummoningsTokenEffect extends OneShotEffect {

    public MetallurgicSummoningsTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create an X/X colorless Construct artifact creature token, where X is that spell's converted mana cost";
    }

    public MetallurgicSummoningsTokenEffect(MetallurgicSummoningsTokenEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell == null) {
            spell = (Spell) game.getLastKnownInformation(((FixedTarget) getTargetPointer()).getTarget(), Zone.STACK);
        }
        if (spell != null) {
            int cmc = spell.getConvertedManaCost();
            if (cmc > 0) {
                return new CreateTokenEffect(new MetallurgicSummoningsConstructToken(cmc)).apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public MetallurgicSummoningsTokenEffect copy() {
        return new MetallurgicSummoningsTokenEffect(this);
    }
}

class MetallurgicSummoningsReturnEffect extends OneShotEffect {

    MetallurgicSummoningsReturnEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return all instant and sorcery cards from your graveyard to your hand. Activate this ability only if you control six or more artifacts";
    }

    MetallurgicSummoningsReturnEffect(final MetallurgicSummoningsReturnEffect effect) {
        super(effect);
    }

    @Override
    public MetallurgicSummoningsReturnEffect copy() {
        return new MetallurgicSummoningsReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.moveCards(controller.getGraveyard().getCards(new FilterInstantOrSorceryCard(), source.getSourceId(),
                    source.getControllerId(), game), Zone.HAND, source, game);
        }
        return false;
    }
}

class MetallurgicSummoningsConstructToken extends Token {

    public MetallurgicSummoningsConstructToken(int xValue) {
        super("Construct", "X/X colorless Construct artifact creature token");
        setOriginalExpansionSetCode("KLD");
        setTokenType(RandomUtil.nextInt(2) + 1);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add("Construct");
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }
}
