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

package mage.sets.newphyrexia;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColorOrArtifact;
import mage.filter.Filter.ComparisonScope;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public class ApostlesBlessing extends CardImpl<ApostlesBlessing> {
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact or creature you control");

    static {
        filter.getCardType().add(CardType.ARTIFACT);
        filter.getCardType().add(CardType.CREATURE);
        filter.setScopeCardType(mage.filter.Filter.ComparisonScope.Any);
    }

    public ApostlesBlessing (UUID ownerId) {
        super(ownerId, 2, "Apostle's Blessing", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{WP}");
        this.expansionSetCode = "NPH";
		this.color.setWhite(true);
        this.getSpellAbility().addEffect(new ApostlesBlessingEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetControlledPermanent(filter));
        this.getSpellAbility().addChoice(new ChoiceColorOrArtifact());
    }

    public ApostlesBlessing (final ApostlesBlessing card) {
        super(card);
    }

    @Override
    public ApostlesBlessing copy() {
        return new ApostlesBlessing(this);
    }

}

class ApostlesBlessingEffect extends GainAbilityTargetEffect {

	FilterCard protectionFilter;

	public ApostlesBlessingEffect(Duration duration) {
		super(new ProtectionAbility(new FilterCard()), duration);
		protectionFilter = (FilterCard)((ProtectionAbility)ability).getFilter();
		staticText = "Target artifact or creature gains protection from artifacts or from the color of your choice until end of turn";
	}

	public ApostlesBlessingEffect(final ApostlesBlessingEffect effect) {
		super(effect);
		this.protectionFilter = effect.protectionFilter.copy();
	}

	@Override
	public ApostlesBlessingEffect copy() {
		return new ApostlesBlessingEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		ChoiceColorOrArtifact choice = (ChoiceColorOrArtifact) source.getChoices().get(0);
        if (choice.isArtifactSelected()) {
            if (!protectionFilter.getCardType().contains(CardType.ARTIFACT)) {
                protectionFilter.getCardType().add(Constants.CardType.ARTIFACT);
                protectionFilter.setScopeCardType(ComparisonScope.Any);
            }
        } else {
            protectionFilter.setColor(choice.getColor());
            protectionFilter.setUseColor(true);
		    protectionFilter.setScopeColor(ComparisonScope.Any);
        }

		protectionFilter.setMessage(choice.getChoice());
        ((ProtectionAbility)ability).setFilter(protectionFilter);
		Permanent creature = game.getPermanent(source.getFirstTarget());
		if (creature != null) {
			creature.addAbility(ability, game);
			return true;
		}
		return false;
	}

}
