package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class BattletideAlchemist extends CardImpl {

    public BattletideAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.KITHKIN, SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // If a source would deal damage to a player, you may prevent X of that damage, where X is the number of Clerics you control.
        this.addAbility(new SimpleStaticAbility(new BattletideAlchemistEffect()));
    }

    private BattletideAlchemist(final BattletideAlchemist card) {
        super(card);
    }

    @Override
    public BattletideAlchemist copy() {
        return new BattletideAlchemist(this);
    }
}

class BattletideAlchemistEffect extends PreventionEffectImpl {

    BattletideAlchemistEffect() {
        super(Duration.WhileOnBattlefield, 0, false, false, new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.CLERIC, "Clerics")));
        this.staticText = "If a source would deal damage to a player, you may prevent X of that damage, where X is the number of Clerics you control";
    }

    private BattletideAlchemistEffect(final BattletideAlchemistEffect effect) {
        super(effect);
    }

    @Override
    public BattletideAlchemistEffect copy() {
        return new BattletideAlchemistEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(event.getTargetId());
        if (controller != null && targetPlayer != null) {
            if (amountToPrevent > 0 && controller.chooseUse(Outcome.PreventDamage, "Prevent " + amountToPrevent + " damage to " + targetPlayer.getName() + '?', source, game)) {
                    preventDamageAction(event, source, game);
                }
            }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

}
