

package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DragonTokenGold;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Saga
 */
public final class SwordOfDungeonsAndDragons extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("Rogues and from Clerics");
     static {filter.add(Predicates.or(
            SubType.ROGUE.getPredicate(),
            SubType.CLERIC.getPredicate()
            ));
    }

    public SwordOfDungeonsAndDragons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from Rogues and from Clerics.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2));
        Effect effect = new GainAbilityAttachedEffect(new ProtectionAbility(filter), AttachmentType.EQUIPMENT);
        effect.setText("and has protection from Rogues and from Clerics");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, you create a 4/4 gold Dragon creature token with flying and roll a d20. If you roll a 20, repeat this process.
        this.addAbility(new SwordOfDungeonsAndDragonsAbility());

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private SwordOfDungeonsAndDragons(final SwordOfDungeonsAndDragons card) {
        super(card);
    }

    @Override
    public SwordOfDungeonsAndDragons copy() {
        return new SwordOfDungeonsAndDragons(this);
    }
}

class SwordOfDungeonsAndDragonsAbility extends TriggeredAbilityImpl {

    public SwordOfDungeonsAndDragonsAbility() {
        super(Zone.BATTLEFIELD, new SwordOfDungeonsAndDragonsEffect(),false);
    }

    public SwordOfDungeonsAndDragonsAbility(final SwordOfDungeonsAndDragonsAbility ability) {
        super(ability);
    }

    @Override
    public SwordOfDungeonsAndDragonsAbility copy() {
        return new SwordOfDungeonsAndDragonsAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
        Permanent p = game.getPermanent(event.getSourceId());
        if (damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, you create a 4/4 gold Dragon creature token with flying and roll a d20. If you roll a 20, repeat this process.";
    }
}

class SwordOfDungeonsAndDragonsEffect extends OneShotEffect {
    
    public SwordOfDungeonsAndDragonsEffect() {
        super(Outcome.Benefit);
    }

    public SwordOfDungeonsAndDragonsEffect(final SwordOfDungeonsAndDragonsEffect effect) {
        super(effect);
    }

    @Override
    public SwordOfDungeonsAndDragonsEffect copy() {
        return new SwordOfDungeonsAndDragonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int count = 1;
            int amount = controller.rollDice(outcome, source, game, 20);

            while (amount == 20) {
                count += 1;
                amount = controller.rollDice(outcome, source, game, 20);
            }
            return new CreateTokenEffect(new DragonTokenGold(), count).apply(game, source);
        }
        return false;
    }
}
