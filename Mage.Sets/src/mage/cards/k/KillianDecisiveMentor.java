package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.permanent.EnchantedBySourceControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class KillianDecisiveMentor extends CardImpl {

    private static final FilterPermanent filter = new FilterEnchantmentPermanent("an enchantment");

    public KillianDecisiveMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever an enchantment you control enters, tap up to one target creature and goad it.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new TapTargetEffect(), filter);
        ability.addEffect(new GoadTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());

        // Whenever one or more creatures that are enchanted by an Aura you control attack, draw a card.
        this.addAbility(new KillianDecisiveMentorTriggeredAbility());
    }

    private KillianDecisiveMentor(final KillianDecisiveMentor card) {
        super(card);
    }

    @Override
    public KillianDecisiveMentor copy() {
        return new KillianDecisiveMentor(this);
    }
}

class KillianDecisiveMentorTriggeredAbility extends TriggeredAbilityImpl {

    KillianDecisiveMentorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.setTriggerPhrase("Whenever one or more creatures that are enchanted by an Aura you control attack");
    }

    private KillianDecisiveMentorTriggeredAbility(final KillianDecisiveMentorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KillianDecisiveMentorTriggeredAbility copy() {
        return new KillianDecisiveMentorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
    FilterAttackingCreature attackers =
        new FilterAttackingCreature("creatures that are enchanted by an Aura you control");
        attackers.add(EnchantedBySourceControllerPredicate.instance);

        return !game.getCombat().getAttackers().isEmpty()
            && !game.getBattlefield().getActivePermanents(attackers, game.getCombat().getAttackingPlayerId(), game).isEmpty();
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures that are enchanted by an Aura you control attack, draw a card.";
    }
}
