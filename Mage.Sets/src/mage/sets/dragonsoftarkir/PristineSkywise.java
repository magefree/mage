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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class PristineSkywise extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("a noncreature spell");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public PristineSkywise(UUID ownerId) {
        super(ownerId, 228, "Pristine Skywise", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Dragon");
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever you cast a noncreature spell, untap Pristine Skywise. It gains protection from the color of your choice until the end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), filter, false);
        ability.addEffect(new GainProtectionFromColorSourceEffect(Duration.EndOfTurn));
        this.addAbility(ability);
    }

    public PristineSkywise(final PristineSkywise card) {
        super(card);
    }

    @Override
    public PristineSkywise copy() {
        return new PristineSkywise(this);
    }
}

class GainProtectionFromColorSourceEffect extends GainAbilitySourceEffect {

    FilterCard protectionFilter;

    public GainProtectionFromColorSourceEffect(Duration duration) {
        super(new ProtectionAbility(new FilterCard()), duration);
        protectionFilter = (FilterCard)((ProtectionAbility)ability).getFilter();
    }

    public GainProtectionFromColorSourceEffect(final GainProtectionFromColorSourceEffect effect) {
        super(effect);
        this.protectionFilter = effect.protectionFilter.copy();
    }

    @Override
    public GainProtectionFromColorSourceEffect copy() {
        return new GainProtectionFromColorSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game); 
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {        
            ChoiceColor colorChoice = new ChoiceColor(true);
            colorChoice.setMessage("Choose color for protection ability");
            while (!colorChoice.isChosen()) {
                controller.choose(outcome, colorChoice, game);
                if (!controller.isInGame()) {
                    discard();
                    return;
                }
            }
            protectionFilter.add(new ColorPredicate(colorChoice.getColor()));
            protectionFilter.setMessage(colorChoice.getChoice());
            ((ProtectionAbility)ability).setFilter(protectionFilter);              
            return;
        }
        discard();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && new MageObjectReference(permanent).refersTo(source.getSourceObject(game))) {
            permanent.addAbility(ability, source.getSourceId(), game);
        } else {
            // the source permanent is no longer on the battlefield, effect can be discarded
            discard();
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "{this} gains protection from the color of your choice " + duration.toString();
    }
}
