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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class FrontierSiege extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with flying");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("a creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(new ControllerPredicate(TargetController.NOT_YOU));
    }
    private final static String ruleTrigger1 = "&bull Khans &mdash; At the beginning of each of your main phases, add {G}{G} to your mana pool.";
    private final static String ruleTrigger2 = "&bull Dragons &mdash; Whenever a creature with flying enters the battlefield under your control, you may have it fight target creature you don't control.";

    public FrontierSiege(UUID ownerId) {
        super(ownerId, 131, "Frontier Siege", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.expansionSetCode = "FRF";

        // As Frontier Siege enters the battlefield, choose Khans or Dragons.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Khans or Dragons?","Khans", "Dragons"),null, true,
                "As {this} enters the battlefield, choose Khans or Dragons.",""));

        // * Khans - At the beginning of each of your main phases, add {G}{G} to your mana pool.
        this.addAbility(new ConditionalTriggeredAbility(
                new FrontierSiegeKhansTriggeredAbility(),
                new ModeChoiceSourceCondition("Khans"),
                ruleTrigger1));

        // * Dragons - Whenever a creature with flying enters the battlefield under your control, you may have it fight target creature you don't control.
        Ability ability2 = new ConditionalTriggeredAbility(
                new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new FrontierSiegeFightEffect(), filter, true, SetTargetPointer.PERMANENT, ""),
                new ModeChoiceSourceCondition("Dragons"),
                ruleTrigger2, true);
        ability2.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability2);

    }

    public FrontierSiege(final FrontierSiege card) {
        super(card);
    }

    @Override
    public FrontierSiege copy() {
        return new FrontierSiege(this);
    }
}

class FrontierSiegeKhansTriggeredAbility extends TriggeredAbilityImpl {

    public FrontierSiegeKhansTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaToManaPoolSourceControllerEffect(new Mana(0,2,0,0,0,0,0)), false);

    }

    public FrontierSiegeKhansTriggeredAbility(final FrontierSiegeKhansTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FrontierSiegeKhansTriggeredAbility copy() {
        return new FrontierSiegeKhansTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PRECOMBAT_MAIN_PHASE_PRE
                || event.getType() == GameEvent.EventType.POSTCOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public String getRule() {
        return "At the beginning of each of your main phases, " + super.getRule();
    }

}

class FrontierSiegeFightEffect extends OneShotEffect {

    FrontierSiegeFightEffect() {
        super(Outcome.Damage);
    }

    FrontierSiegeFightEffect(final FrontierSiegeFightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeredCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (triggeredCreature != null
                && target != null
                && triggeredCreature.getCardType().contains(CardType.CREATURE)
                && target.getCardType().contains(CardType.CREATURE)) {
            triggeredCreature.fight(target, source, game);
            return true;
        }
        return false;
    }

    @Override
    public FrontierSiegeFightEffect copy() {
        return new FrontierSiegeFightEffect(this);
    }
}
