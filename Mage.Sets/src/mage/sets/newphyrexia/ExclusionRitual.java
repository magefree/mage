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
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 * @author Loki
 */
public class ExclusionRitual extends CardImpl<ExclusionRitual> {
    private static final FilterPermanent filter = new FilterPermanent("nonland permanent");

    static {
        filter.getNotCardType().add(CardType.LAND);
    }

    public ExclusionRitual(UUID ownerId) {
        super(ownerId, 10, "Exclusion Ritual", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");
        this.expansionSetCode = "NPH";

        this.color.setWhite(true);

        // Imprint - When Exclusion Ritual enters the battlefield, exile target nonland permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExclusionRitualImprintEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
        // Players can't cast spells with the same name as the exiled card.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ExclusionRitualReplacementEffect()));
    }

    public ExclusionRitual(final ExclusionRitual card) {
        super(card);
    }

    @Override
    public ExclusionRitual copy() {
        return new ExclusionRitual(this);
    }
}

class ExclusionRitualImprintEffect extends OneShotEffect<ExclusionRitualImprintEffect> {
    ExclusionRitualImprintEffect() {
        super(Constants.Outcome.Exile);
        staticText = "exile target nonland permanent";
    }

    ExclusionRitualImprintEffect(final ExclusionRitualImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(source));
        if (sourcePermanent != null && targetPermanent != null) {
            targetPermanent.moveToExile(getId(), "Exclusion Ritual (Imprint)", source.getSourceId(), game);
            sourcePermanent.imprint(targetPermanent.getId(), game);
        }
        return true;
    }

    @Override
    public ExclusionRitualImprintEffect copy() {
        return new ExclusionRitualImprintEffect(this);
    }
}

class ExclusionRitualReplacementEffect extends ReplacementEffectImpl<ExclusionRitualReplacementEffect> {
    ExclusionRitualReplacementEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Detriment);
        staticText = "Players can't cast spells with the same name as the exiled card";
    }

    ExclusionRitualReplacementEffect(final ExclusionRitualReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            Card card = game.getCard(event.getSourceId());
            if (sourcePermanent != null && card != null) {
                if (sourcePermanent.getImprinted().size() > 0) {
                    Card imprintedCard = game.getCard(sourcePermanent.getImprinted().get(0));
                    if (imprintedCard != null) {
                        return card.getName().equals(imprintedCard.getName());
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public ExclusionRitualReplacementEffect copy() {
        return new ExclusionRitualReplacementEffect(this);
    }
}
