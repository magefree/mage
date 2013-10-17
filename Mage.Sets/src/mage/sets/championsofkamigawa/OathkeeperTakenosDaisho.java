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
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.EquippedMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlAttachedEffect;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class OathkeeperTakenosDaisho extends CardImpl<OathkeeperTakenosDaisho> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("it's a Samurai card");
    static {
        filter.add(new SubtypePredicate("Samurai"));
    }

    public OathkeeperTakenosDaisho(UUID ownerId) {
        super(ownerId, 265, "Oathkeeper, Takeno's Daisho", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Equipment");

        // Equipped creature gets +3/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3,1, Duration.WhileOnBattlefield)));
        // Whenever equipped creature dies, return that card to the battlefield under your control if it's a Samurai card.
        this.addAbility(new ConditionalTriggeredAbility(
                new DiesAttachedTriggeredAbility(new ReturnToBattlefieldUnderYourControlAttachedEffect(),"equipped creature", false),
                new OathkeeperEquippedMatchesFilterCondition(filter),
                ""));
        // When Oathkeeper, Takeno's Daisho is put into a graveyard from the battlefield, exile equipped creature.
        this.addAbility(new PutIntoGraveFromBattlefieldTriggeredAbility(new ExileEquippedEffect()));
        // Equip {2}
        this.addAbility(new EquipAbility( Outcome.BoostCreature, new ManaCostsImpl("{2}")));
    }

    public OathkeeperTakenosDaisho(final OathkeeperTakenosDaisho card) {
        super(card);
    }

    @Override
    public OathkeeperTakenosDaisho copy() {
        return new OathkeeperTakenosDaisho(this);
    }
}

class ExileEquippedEffect extends OneShotEffect<ExileEquippedEffect> {

    public ExileEquippedEffect() {
        super(Outcome.Exile);
        staticText = "exile equipped creature";
    }

    public ExileEquippedEffect(final ExileEquippedEffect effect) {
        super(effect);
    }

    @Override
    public ExileEquippedEffect copy() {
        return new ExileEquippedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(equipment.getAttachedTo());
            if (creature != null) {
                return creature.moveToExile(null, "", source.getSourceId(), game);
            }
        }
        return false;
    }

}

class OathkeeperEquippedMatchesFilterCondition implements Condition {

    private FilterCreaturePermanent filter;

    public OathkeeperEquippedMatchesFilterCondition(FilterCreaturePermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (permanent != null) {
            Permanent attachedTo = null;
            if (permanent.getAttachedTo() != null) {
                attachedTo = game.getBattlefield().getPermanent(permanent.getAttachedTo());
                if (attachedTo == null) {
                    attachedTo = (Permanent) game.getLastKnownInformation(permanent.getAttachedTo(), Zone.BATTLEFIELD);
                }
            }
            if (attachedTo == null) {
                for (Effect effect :source.getEffects()) {
                    attachedTo = (Permanent) effect.getValue("attachedTo");
                }
            }
            if (attachedTo != null) {
                if (filter.match(attachedTo, attachedTo.getId(),attachedTo.getControllerId(), game)) {
                    return true;
                }

            }
        }
        return false;
    }
}