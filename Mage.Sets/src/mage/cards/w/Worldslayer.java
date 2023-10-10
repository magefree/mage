
package mage.cards.w;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public final class Worldslayer extends CardImpl {

    public Worldslayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature deals combat damage to a player, destroy all permanents other than Worldslayer.
        this.addAbility(new WorldslayerTriggeredAbility());
        
        // Equip {5}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(5)));
    }

    private Worldslayer(final Worldslayer card) {
        super(card);
    }

    @Override
    public Worldslayer copy() {
        return new Worldslayer(this);
    }
}

class WorldslayerTriggeredAbility extends TriggeredAbilityImpl {

    WorldslayerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WorldslayerEffect(), false);
    }

    private WorldslayerTriggeredAbility(final WorldslayerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WorldslayerTriggeredAbility copy() {
        return new WorldslayerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent p = game.getPermanent(event.getSourceId());
        return damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, destroy all permanents other than {this}.";
    }
}

class WorldslayerEffect extends OneShotEffect {

    public WorldslayerEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy all permanents other than {this}";
    }

    private WorldslayerEffect(final WorldslayerEffect effect) {
        super(effect);
    }

    @Override
    public WorldslayerEffect copy() {
        return new WorldslayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            if (!Objects.equals(permanent.getId(), source.getSourceId())) {
                permanent.destroy(source, game, false);
            }
        }
        return true;
    }
}
