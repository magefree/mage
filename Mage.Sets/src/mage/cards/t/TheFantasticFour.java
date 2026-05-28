package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.WallColorlessToken;
import mage.game.stack.Spell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class TheFantasticFour extends CardImpl {

    public TheFantasticFour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When The Fantastic Four enter and whenever you cast a spell with power, toughness, or mana value 4,
        // choose one that hasn't been chosen this turn —
        // * Create a 0/4 colorless Wall creature token with defender.
        Ability ability = new TheFantasticFourTriggeredAbility();
        ability.setModeTag("create wall token");
        ability.getModes().setLimitUsageByOnce(true);
        // * The Fantastic Four deal 3 damage to each opponent.
        ability.addMode(new Mode(new DamagePlayersEffect(3, TargetController.OPPONENT))
            .setModeTag("deal damage to opponents"));
        // * Put two +1/+1 counters on target creature.
        Mode mode3 = new Mode(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)))
            .setModeTag("put counters on creature");
        mode3.addTarget(new TargetCreaturePermanent());
        ability.addMode(mode3);
        // * Draw a card.
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(1)).setModeTag("draw card"));
        ability.addHint(ModesAlreadyUsedHint.instance);
        this.addAbility(ability);
    }

    private TheFantasticFour(final TheFantasticFour card) {
        super(card);
    }

    @Override
    public TheFantasticFour copy() {
        return new TheFantasticFour(this);
    }
}

class TheFantasticFourTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with power, toughness, or mana value 4");

    static {
        filter.add(
            Predicates.or(
                new ManaValuePredicate(ComparisonType.EQUAL_TO, 4),
                new PowerPredicate(ComparisonType.EQUAL_TO, 4),
                new ToughnessPredicate(ComparisonType.EQUAL_TO, 4)
            )
        );
    }

    TheFantasticFourTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new WallColorlessToken()), false);
        setTriggerPhrase("When {this} enters and whenever you cast " + filter.getMessage() + ", ");
    }

    private TheFantasticFourTriggeredAbility(final TheFantasticFourTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheFantasticFourTriggeredAbility copy() {
        return new TheFantasticFourTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
            || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return event.getTargetId().equals(getSourceId());
        }
        // SPELL_CAST
        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && filter.match(spell, getControllerId(), this, game);
    }
}
