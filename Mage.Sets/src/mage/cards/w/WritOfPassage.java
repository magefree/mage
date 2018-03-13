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
package mage.cards.w;

import java.util.UUID;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ForecastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public class WritOfPassage extends CardImpl {

    public WritOfPassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature attacks, if its power is 2 or less, it's unblockable this turn.
        FilterPermanent filter = new FilterPermanent("if enchanted creature's power is 2 or less");
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        ability = new ConditionalTriggeredAbility(new AttacksAttachedTriggeredAbility(
                new WritOfPassageAttachedEffect(AttachmentType.AURA), AttachmentType.AURA, false),
                new AttachedToMatchesFilterCondition(filter), "Whenever enchanted creature attacks, if its power is 2 or less, it can't be blocked this turn.");
        this.addAbility(ability);
        // Forecast - {1}{U}, Reveal Writ of Passage from your hand: Target creature with power 2 or less is unblockable this turn.
        ForecastAbility ability2 = new ForecastAbility(new CantBeBlockedTargetEffect(), new ManaCostsImpl("{1}{U}"));
        FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature with power 2 or less");
        filter2.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        ability2.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability2);
    }

    public WritOfPassage(final WritOfPassage card) {
        super(card);
    }

    @Override
    public WritOfPassage copy() {
        return new WritOfPassage(this);
    }
}

class WritOfPassageAttachedEffect extends RestrictionEffect {

    public WritOfPassageAttachedEffect(AttachmentType attachmentType) {
        super(Duration.EndOfTurn);
        this.staticText = attachmentType.verb() + " creature can't be blocked";
    }

    public WritOfPassageAttachedEffect(WritOfPassageAttachedEffect effect) {
        super(effect);
    }

    @Override
    public WritOfPassageAttachedEffect copy() {
        return new WritOfPassageAttachedEffect(this);
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        return attachment != null && attachment.getAttachedTo() != null
                && attachment.getAttachedTo().equals(permanent.getId());
    }
}
