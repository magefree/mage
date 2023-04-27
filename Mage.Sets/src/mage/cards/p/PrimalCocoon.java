

package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.counter.AddPlusOneCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class PrimalCocoon extends CardImpl {

    public PrimalCocoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");

        this.subtype.add(SubType.AURA);

        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        this.addAbility(new PrimalCocoonAbility1());
        this.addAbility(new PrimalCocoonAbility2());

    }

    private PrimalCocoon(final PrimalCocoon card) {
        super(card);
    }

    @Override
    public PrimalCocoon copy() {
        return new PrimalCocoon(this);
    }
}

class PrimalCocoonAbility1 extends TriggeredAbilityImpl {

    public PrimalCocoonAbility1() {
        super(Zone.BATTLEFIELD, new AddPlusOneCountersAttachedEffect(1));
    }

    public PrimalCocoonAbility1(final PrimalCocoonAbility1 ability) {
        super(ability);
    }

    @Override
    public PrimalCocoonAbility1 copy() {
        return new PrimalCocoonAbility1(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, put a +1/+1 counter on enchanted creature.";
    }
}

class PrimalCocoonAbility2 extends TriggeredAbilityImpl {

    public PrimalCocoonAbility2() {
        super(Zone.BATTLEFIELD, new DestroySourceEffect());
    }

    public PrimalCocoonAbility2(final PrimalCocoonAbility2 ability) {
        super(ability);
    }

    @Override
    public PrimalCocoonAbility2 copy() {
        return new PrimalCocoonAbility2(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED  || event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(sourceId);
        return enchantment != null && enchantment.getAttachedTo() != null && event.getSourceId() != null && event.getSourceId().equals(enchantment.getAttachedTo());
    }

    @Override
    public String getRule() {
        return "When enchanted creature attacks or blocks, sacrifice {this}.";
    }

}
