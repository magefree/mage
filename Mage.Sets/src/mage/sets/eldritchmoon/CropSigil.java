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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public class CropSigil extends CardImpl {

    private static final FilterCard filterCreature = new FilterCard("creature card in a graveyard");
    private static final FilterCard filterLand = new FilterCard("land card in a graveyard");

    static {
        filterCreature.add(new CardTypePredicate(CardType.CREATURE));
        filterLand.add(new CardTypePredicate(CardType.LAND));
    }

    public CropSigil(UUID ownerId) {
        super(ownerId, 153, "Crop Sigil", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.expansionSetCode = "EMN";

        // At the beginning of your upkeep, you may put the top card of your library into your graveyard.
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new PutTopCardOfLibraryIntoGraveControllerEffect(1), true));

        // <i>Delirium</i> &mdash; {2}{G}, Sacrifice Crop Sigil: Return up to one target creature card and up to one target land card from your graveyard to your hand.
        // Activate this ability only if there are four or more card types among cards in your graveyard.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(true), new ManaCostsImpl<>("{2}{G}"),
                DeliriumCondition.getInstance(),
                "<i>Delirium</i> &mdash; {2}{G}, Sacrifice {this}: Return up to one target creature card and up to one target land card from your graveyard to your hand. "
                + "Activate this ability only if there are four or more card types among cards in your graveyard");
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filterCreature));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filterLand));
        this.addAbility(ability);
    }

    public CropSigil(final CropSigil card) {
        super(card);
    }

    @Override
    public CropSigil copy() {
        return new CropSigil(this);
    }
}
