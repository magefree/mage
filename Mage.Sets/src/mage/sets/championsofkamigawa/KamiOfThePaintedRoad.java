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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterSpiritOrArcaneCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class KamiOfThePaintedRoad extends CardImpl<KamiOfThePaintedRoad> {

    private static final FilterSpiritOrArcaneCard filter = new FilterSpiritOrArcaneCard();

    public KamiOfThePaintedRoad(UUID ownerId) {
        super(ownerId, 23, "Kami of the Painted Road", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Spirit");

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a Spirit or Arcane spell, Kami of the Painted Road gains protection from the color of your choice until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new GainProtectionFromColorSourceEffect(Duration.EndOfTurn), filter, true);
        Choice colorChoice = new ChoiceColor();
        colorChoice.setMessage("Choose color (Kami of the Painted Road)");
        ability.addChoice(new ChoiceColor());
        this.addAbility(ability);

    }

    public KamiOfThePaintedRoad(final KamiOfThePaintedRoad card) {
        super(card);
    }

    @Override
    public KamiOfThePaintedRoad copy() {
        return new KamiOfThePaintedRoad(this);
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
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getSourceId());
        if (creature != null) {
            ChoiceColor choice = (ChoiceColor) source.getChoices().get(0);
            protectionFilter.add(new ColorPredicate(choice.getColor()));
            protectionFilter.setMessage(choice.getChoice());
            ((ProtectionAbility)ability).setFilter(protectionFilter);
            creature.addAbility(ability, game);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "{this} gains protection from the color of your choice " + duration.toString();
    }
}
