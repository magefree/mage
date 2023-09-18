package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.ManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TyvarTheBellicose extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ELF, "");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public TyvarTheBellicose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever one or more Elves you control attack, they gain deathtouch until end of turn.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                Zone.BATTLEFIELD, new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
                .setText("they gain deathtouch until end of turn"), 1, filter, true
        ).setTriggerPhrase("Whenever one or more Elves you control attack, "));

        // Each creature you control has "Whenever a mana ability of this creature resolves, put a number of +1/+1 counters on it equal to the amount of mana this creature produced. This ability triggers only once each turn."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new TyvarTheBellicoseTriggeredAbility(),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("each creature you control has \"Whenever a mana ability of this creature resolves, " +
                "put a number of +1/+1 counters on it equal to the amount of mana this creature produced. " +
                "This ability triggers only once each turn.\"")));
    }

    private TyvarTheBellicose(final TyvarTheBellicose card) {
        super(card);
    }

    @Override
    public TyvarTheBellicose copy() {
        return new TyvarTheBellicose(this);
    }
}

class TyvarTheBellicoseTriggeredAbility extends TriggeredAbilityImpl {

    TyvarTheBellicoseTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0), SavedDamageValue.MANY, false
        ));
        this.setTriggersOnceEachTurn(true);
    }

    private TyvarTheBellicoseTriggeredAbility(final TyvarTheBellicoseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TyvarTheBellicoseTriggeredAbility copy() {
        return new TyvarTheBellicoseTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MANA_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getPlayerId())
                && event.getSourceId().equals(getSourceId())
                && game
                .getAbility(event.getTargetId(), event.getSourceId())
                .map(ManaAbility.class::isInstance)
                .orElse(false)) {
            this.getEffects().setValue("damage", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a mana ability of this creature resolves, put a number of +1/+1 counters on it " +
                "equal to the amount of mana this creature produced. This ability triggers only once each turn.";
    }
}
