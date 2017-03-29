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
package mage.cards.c;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.v.VoraciousReader;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class CuriousHomunculus extends CardImpl {

    public CuriousHomunculus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add("Homunculus");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.transformable = true;
        this.secondSideCardClazz = VoraciousReader.class;

        // {T}: Add {C} to your mana pool. Spend this mana only to cast an instant or sorcery spell.
        this.addAbility(new ConditionalColorlessManaAbility(new TapSourceCost(), 1, new InstantOrSorcerySpellManaBuilder()));

        // At the beginning of your upkeep, if there are three or more instant and/or sorcery cards in your graveyard, transform Curious Homunculus.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.YOU, false),
                new InstantOrSorceryCardsInControllerGraveCondition(3),
                "At the beginning of your upkeep, if there are three or more instant and/or sorcery cards in your graveyard, transform {this}"));
    }

    public CuriousHomunculus(final CuriousHomunculus card) {
        super(card);
    }

    @Override
    public CuriousHomunculus copy() {
        return new CuriousHomunculus(this);
    }
}

class InstantOrSorcerySpellManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new InstantOrSorceryCastConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an instant or sorcery spell";
    }
}

class InstantOrSorceryCastConditionalMana extends ConditionalMana {

    public InstantOrSorceryCastConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast an instant or sorcery spell";
        addCondition(new InstantOrSorceryCastManaCondition());
    }
}

class InstantOrSorceryCastManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source.getSourceId());
            if (object != null && (object.isInstant() || object.isSorcery())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}

class InstantOrSorceryCardsInControllerGraveCondition implements Condition {

    private int value;

    public InstantOrSorceryCardsInControllerGraveCondition(int value) {
        this.value = value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player p = game.getPlayer(source.getControllerId());
        if (p != null && p.getGraveyard().count(new FilterInstantOrSorceryCard(), game) >= value) {
            return true;
        }
        return false;
    }
}
