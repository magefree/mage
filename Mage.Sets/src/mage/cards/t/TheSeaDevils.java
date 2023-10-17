package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AlienSalamanderToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.GameLog;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TheSeaDevils extends CardImpl {

    public TheSeaDevils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Create a 2/2 green Alien Salamander creature token with islandwalk.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new CreateTokenEffect(new AlienSalamanderToken())
        );

        // III -- Until end of turn, whenever a Salamander deals combat damage to a player, it deals that much damage to target creature that player controls.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new CreateDelayedTriggeredAbilityEffect(new TheSeaDevilsTrigger())
        );

        this.addAbility(sagaAbility);
    }

    private TheSeaDevils(final TheSeaDevils card) {
        super(card);
    }

    @Override
    public TheSeaDevils copy() {
        return new TheSeaDevils(this);
    }
}

class TheSeaDevilsTrigger extends DelayedTriggeredAbility {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SALAMANDER, "Salamander");

    TheSeaDevilsTrigger() {
        super(new TheSeaDevilsEffect(), Duration.EndOfTurn, false);
    }

    private TheSeaDevilsTrigger(final TheSeaDevilsTrigger ability) {
        super(ability);
    }

    @Override
    public TheSeaDevilsTrigger copy() {
        return new TheSeaDevilsTrigger(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!((DamagedPlayerEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent salamander = game.getPermanent(event.getSourceId());
        if (!filter.match(salamander, getControllerId(), this, game)) {
            return false;
        }

        Player player = game.getPlayer(event.getTargetId());
        MageObjectReference salamanderMOR = new MageObjectReference(salamander, game);
        if (salamanderMOR == null || player == null) {
            return false;
        }

        this.getTargets().clear();
        FilterCreaturePermanent filterTarget = new FilterCreaturePermanent("creature " + player.getName() + " controls");
        filterTarget.add(new ControllerIdPredicate(player.getId()));
        this.addTarget(new TargetCreaturePermanent(filterTarget));

        int amount = event.getAmount();

        this.getEffects().setValue("damage", amount);
        this.getEffects().setValue("sourceMOR", salamanderMOR);

        for (Effect effect : this.getEffects()) {
            if (effect instanceof TheSeaDevilsEffect) {
                // This is a workaround to add an hint-like message on the ordering trigger panel.
                // Which salamander and how much damage it dealt are both important info to order triggers.
                effect.setTargetPointer(
                        new FixedTarget(event.getSourceId(), game)
                                .withData("damageAmount", "" + amount)
                                .withData("triggeredName", GameLog.getColoredObjectIdNameForTooltip(salamander))
                );
            }
        }

        return true;
    }

    @Override
    public String getRule() {
        // that triggers depends on stack order, so make each trigger unique with extra info
        String triggeredInfo = "";
        if (this.getEffects().get(0).getTargetPointer() != null) {
            if (!this.getEffects().get(0).getTargetPointer().getData("damageAmount").isEmpty()) {
                triggeredInfo += "<br><i>Damage: " + this.getEffects().get(0).getTargetPointer().getData("damageAmount") + "</i>";
                triggeredInfo += "<br><i>Salamander: " + this.getEffects().get(0).getTargetPointer().getData("triggeredName") + "</i>";
            }
        }
        return "Until end of turn, whenever a Salamander deals combat damage to a player, "
                + "it deals that much damage to target creature that player controls." + triggeredInfo;
    }
}

class TheSeaDevilsEffect extends OneShotEffect {

    TheSeaDevilsEffect() {
        super(Outcome.Damage);
        staticText = "it deals that much damage to target creature that player controls";
    }

    private TheSeaDevilsEffect(final TheSeaDevilsEffect effect) {
        super(effect);
    }

    @Override
    public TheSeaDevilsEffect copy() {
        return new TheSeaDevilsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObjectReference salamanderMOR = (MageObjectReference) getValue("sourceMOR");
        if (salamanderMOR == null) {
            return false;
        }

        int amount = SavedDamageValue.MUCH.calculate(game, source, this);
        Permanent salamander = salamanderMOR.getPermanentOrLKIBattlefield(game);
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null || salamander == null || target == null) {
            return false;
        }

        target.damage(amount, salamander.getId(), source, game);
        return true;
    }
}