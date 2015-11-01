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
package mage.sets.avacynrestored;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author noxx
 */
public class InfiniteReflection extends CardImpl {

    public InfiniteReflection(UUID ownerId) {
        super(ownerId, 61, "Infinite Reflection", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Copy));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When Infinite Reflection enters the battlefield attached to a creature, each other nontoken creature you control becomes a copy of that creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InfiniteReflectionTriggeredEffect()));

        // Nontoken creatures you control enter the battlefield as a copy of enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new InfiniteReflectionEntersBattlefieldEffect()));
    }

    public InfiniteReflection(final InfiniteReflection card) {
        super(card);
    }

    @Override
    public InfiniteReflection copy() {
        return new InfiniteReflection(this);
    }
}

class InfiniteReflectionTriggeredEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    public InfiniteReflectionTriggeredEffect() {
        super(Outcome.Sacrifice);
        this.staticText = " attached to a creature, each other nontoken creature you control becomes a copy of that creature";
    }

    public InfiniteReflectionTriggeredEffect(final InfiniteReflectionTriggeredEffect effect) {
        super(effect);
    }

    @Override
    public InfiniteReflectionTriggeredEffect copy() {
        return new InfiniteReflectionTriggeredEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null && sourcePermanent.getAttachedTo() != null) {
            Permanent toCopyFromPermanent = game.getPermanent(sourcePermanent.getAttachedTo());
            if (toCopyFromPermanent != null) {
                for (Permanent toCopyToPermanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                    if (!toCopyToPermanent.equals(toCopyFromPermanent) && !(toCopyToPermanent instanceof PermanentToken)) {
                        game.copyPermanent(toCopyFromPermanent, toCopyToPermanent.getId(), source, new EmptyApplyToPermanent());
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class InfiniteReflectionEntersBattlefieldEffect extends ReplacementEffectImpl {

    public InfiniteReflectionEntersBattlefieldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
    }

    public InfiniteReflectionEntersBattlefieldEffect(InfiniteReflectionEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null && permanent.getControllerId().equals(source.getControllerId())
                && permanent.getCardType().contains(CardType.CREATURE)
                && !(permanent instanceof PermanentToken);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        MageObject toCopyToObject = ((EntersTheBattlefieldEvent) event).getTarget();
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null && toCopyToObject != null && sourcePermanent.getAttachedTo() != null) {
            Permanent toCopyFromPermanent = game.getPermanent(sourcePermanent.getAttachedTo());
            if (toCopyFromPermanent != null) {
                game.copyPermanent(toCopyFromPermanent, toCopyToObject.getId(), source, new EmptyApplyToPermanent());
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Nontoken creatures you control enter the battlefield as a copy of enchanted creature";
    }

    @Override
    public InfiniteReflectionEntersBattlefieldEffect copy() {
        return new InfiniteReflectionEntersBattlefieldEffect(this);
    }

}
