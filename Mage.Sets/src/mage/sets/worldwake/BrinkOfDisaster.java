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
package mage.sets.worldwake;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author nantuko
 */
public class BrinkOfDisaster extends CardImpl<BrinkOfDisaster> {

	private static FilterPermanent filter = new FilterPermanent();

	static {
		filter.getCardType().add(CardType.CREATURE);
		filter.getCardType().add(CardType.LAND);
		filter.setScopeCardType(Filter.ComparisonScope.Any);
	}

    public BrinkOfDisaster(UUID ownerId) {
        super(ownerId, 52, "Brink of Disaster", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Aura");

        this.color.setBlack(true);

        // Enchant creature or land
		TargetPermanent auraTarget = new TargetPermanent();
		this.getSpellAbility().addTarget(auraTarget);
		this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.AddAbility));

        // When enchanted permanent becomes tapped, destroy it.
		this.addAbility(new EnchantedBecomesTappedTriggeredAbility(new DestroyTargetEffect()));
    }

    public BrinkOfDisaster(final BrinkOfDisaster card) {
        super(card);
    }

    @Override
    public BrinkOfDisaster copy() {
        return new BrinkOfDisaster(this);
    }
}

class EnchantedBecomesTappedTriggeredAbility extends TriggeredAbilityImpl<EnchantedBecomesTappedTriggeredAbility> {

    public EnchantedBecomesTappedTriggeredAbility(Effect effect) {
        super(Constants.Zone.BATTLEFIELD, effect);
    }

    public EnchantedBecomesTappedTriggeredAbility(final EnchantedBecomesTappedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EnchantedBecomesTappedTriggeredAbility copy() {
        return new EnchantedBecomesTappedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.TAPPED) {
			Permanent enchant = game.getPermanent(sourceId);
			if (enchant != null && enchant.getAttachedTo() != null) {
				if (event.getTargetId().equals(enchant.getAttachedTo())) {
					getEffects().get(0).setTargetPointer(new FixedTarget(enchant.getAttachedTo()));
					return true;
				}
			}
		}
		return false;
    }

    @Override
    public String getRule() {
        return "When enchanted permanent becomes tapped, destroy it.";
    }
}
