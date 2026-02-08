package mage.cards.n;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.SuspectTargetEffect;
import mage.abilities.effects.common.combat.GoadAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.SuspectedPredicate;
import mage.game.Game;
import mage.game.events.DamagedBatchForPlayersEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author jantizio
 */
public final class NellyBorcaImpulsiveAccuser extends CardImpl {

    private static final FilterPermanent suspectedCreaturesFilter = new FilterCreaturePermanent("suspected creatures");

    static {
        suspectedCreaturesFilter.add(SuspectedPredicate.instance);
    }

    public NellyBorcaImpulsiveAccuser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Nelly Borca, Impulsive Accuser attacks, suspect target creature. Then goad all suspected creatures.
        AttacksTriggeredAbility ability = new AttacksTriggeredAbility(new SuspectTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new GoadAllEffect(suspectedCreaturesFilter).setText("Then goad all suspected creatures"));
        this.addAbility(ability);


        // Whenever one or more creatures an opponent controls deal combat damage to one or more of your opponents, you and the controller of those creatures each draw a card.
        this.addAbility(new NellyBorcaTriggeredAbility());


    }

    private NellyBorcaImpulsiveAccuser(final NellyBorcaImpulsiveAccuser card) {
        super(card);
    }

    @Override
    public NellyBorcaImpulsiveAccuser copy() {
        return new NellyBorcaImpulsiveAccuser(this);
    }
}


class NellyBorcaTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent> {

    NellyBorcaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.addEffect(new DrawCardTargetEffect(1));
    }

    private NellyBorcaTriggeredAbility(final TriggeredAbilityImpl ability) {
        super(ability);
    }


    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_PLAYERS;
    }

    @Override
    public boolean checkEvent(DamagedPlayerEvent event, Game game) {
        // exclude non-combat damage
        if (!event.isCombatDamage()) {
            return false;
        }
        // check if damage is dealt to opponent
        if (!game.getOpponents(getControllerId()).contains(event.getTargetId())) {
            return false;
        }

        Permanent sourceCreature = game.getPermanentOrLKIBattlefield(event.getSourceId());
        // check if damage is dealt by opponent
        return sourceCreature != null
                && game.getOpponents(getControllerId()).contains(sourceCreature.getControllerId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        List<DamagedPlayerEvent> events = getFilteredEvents((DamagedBatchForPlayersEvent) event, game);

        UUID sourcePlayerID = events.stream()
                .map(DamagedPlayerEvent::getSourceId)
                .map(game::getControllerId)
                .findFirst().orElse(null);

        if (sourcePlayerID == null) {
            return false;
        }

        this.getEffects().setTargetPointer(new FixedTarget(sourcePlayerID));
        return true;
    }

    @Override
    public NellyBorcaTriggeredAbility copy() {
        return new NellyBorcaTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures an opponent controls deal combat damage to one or more of your opponents, " +
                "you and the controller of those creatures each draw a card.";
    }

}