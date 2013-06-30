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
package mage.sets.modernmasters;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class MoltenDisaster extends CardImpl<MoltenDisaster> {

    public MoltenDisaster(UUID ownerId) {
        super(ownerId, 123, "Molten Disaster", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");
        this.expansionSetCode = "MMA";

        this.color.setRed(true);

        // If Molten Disaster was kicked, it has split second.
        Ability ability = new ConditionalTriggeredAbility(new MoltenDisasterTriggeredAbility(), KickedCondition.getInstance(), "");
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        // Kicker {R}
        this.addAbility(new KickerAbility("{R}"));
        // Molten Disaster deals X damage to each creature without flying and each player.
        this.getSpellAbility().addEffect(new MoltenDisasterEffect());
    }

    public MoltenDisaster(final MoltenDisaster card) {
        super(card);
    }

    @Override
    public MoltenDisaster copy() {
        return new MoltenDisaster(this);
    }
}

class MoltenDisasterTriggeredAbility extends TriggeredAbilityImpl<MoltenDisasterTriggeredAbility> {

    public MoltenDisasterTriggeredAbility() {
        super(Zone.HAND, new GainAbilitySourceEffect(SplitSecondAbility.getInstance(), Duration.WhileOnStack), false);
    }

    public MoltenDisasterTriggeredAbility(final MoltenDisasterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MoltenDisasterTriggeredAbility copy() {
        return new MoltenDisasterTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(GameEvent.EventType.CAST_SPELL) && event.getSourceId().equals(this.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "If Molten Disaster was kicked, it has split second <i>(As long as this spell is on the stack, players can't cast spells or activate abilities that aren't mana abilities.)</i>";
    }
}

class MoltenDisasterEffect extends OneShotEffect<MoltenDisasterEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public MoltenDisasterEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to each creature without flying and each player";
    }

    public MoltenDisasterEffect(final MoltenDisasterEffect effect) {
        super(effect);
    }

    @Override
    public MoltenDisasterEffect copy() {
        return new MoltenDisasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX();
        for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            permanent.damage(amount, source.getId(), game, true, false);
        }
        for (UUID playerId: game.getPlayer(source.getControllerId()).getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.damage(amount, source.getSourceId(), game, false, true);
            }
        }
        return true;
    }

}