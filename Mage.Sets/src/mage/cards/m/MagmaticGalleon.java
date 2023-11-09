package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class MagmaticGalleon extends CardImpl {

    public MagmaticGalleon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}{R}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Magmatic Galleon enters the battlefield, it deals 5 damage to target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(5));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Whenever one or more creatures your opponents control are dealt excess noncombat damage, create a Treasure token.
        this.addAbility(new MagmaticGalleonTriggeredAbility());

        // Crew 2
        this.addAbility(new CrewAbility(2));

    }

    private MagmaticGalleon(final MagmaticGalleon card) {
        super(card);
    }

    @Override
    public MagmaticGalleon copy() {
        return new MagmaticGalleon(this);
    }
}

class MagmaticGalleonTriggeredAbility extends TriggeredAbilityImpl {

    MagmaticGalleonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()));
        setTriggerPhrase("Whenever one or more creatures your opponents control are dealt excess noncombat damage, ");
    }

    private MagmaticGalleonTriggeredAbility(final MagmaticGalleonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MagmaticGalleonTriggeredAbility copy() {
        return new MagmaticGalleonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_PERMANENTS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedBatchEvent dEvent = (DamagedBatchEvent) event;
        int damage = dEvent
                .getEvents()
                .stream()
                .filter(damagedEvent -> {
                    if (damagedEvent.isCombatDamage()) {
                        return false;
                    }
                    Permanent permanent = game.getPermanentOrLKIBattlefield(damagedEvent.getTargetId());
                    return permanent != null && permanent.isCreature(game)
                            && game.getOpponents(this.getControllerId()).contains(permanent.getControllerId());
                })
                .mapToInt(DamagedEvent::getExcess)
                .sum();
        return damage >= 1;
    }

}
