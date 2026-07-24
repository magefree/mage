package mage.cards.p;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.predicate.permanent.GoadedPredicate;
import mage.game.Game;
import mage.game.events.DamagedBatchForPlayersEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 * @author muz
 */
public final class PuppetMasterStringPuller extends CardImpl {

    public PuppetMasterStringPuller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you attack, goad target creature an opponent controls. It can't block this turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new GoadTargetEffect(), 1);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addEffect(new CantBlockTargetEffect(Duration.EndOfTurn).withTargetDescription("It"));
        this.addAbility(ability);

        // Whenever one or more goaded creatures deal combat damage to one of your opponents, create a Treasure token.
        this.addAbility(new PuppetMasterStringPullerTriggeredAbility());
    }

    private PuppetMasterStringPuller(final PuppetMasterStringPuller card) {
        super(card);
    }

    @Override
    public PuppetMasterStringPuller copy() {
        return new PuppetMasterStringPuller(this);
    }
}

class PuppetMasterStringPullerTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent> {

    PuppetMasterStringPullerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()));
        setTriggerPhrase("Whenever one or more goaded creatures deal combat damage to one or more of your opponents, ");
    }

    private PuppetMasterStringPullerTriggeredAbility(final PuppetMasterStringPullerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_PLAYERS;
    }

    @Override
    public boolean checkEvent(DamagedPlayerEvent event, Game game) {
        if (!event.isCombatDamage() || !game.getOpponents(getControllerId()).contains(event.getTargetId())) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null && GoadedPredicate.instance.apply(permanent, game);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Set<UUID> opponents = new HashSet<>();
        getFilteredEvents((DamagedBatchForPlayersEvent) event, game)
                .stream()
                .map(GameEvent::getTargetId)
                .forEach(opponents::add);
        return !opponents.isEmpty();
    }

    @Override
    public PuppetMasterStringPullerTriggeredAbility copy() {
        return new PuppetMasterStringPullerTriggeredAbility(this);
    }
}
