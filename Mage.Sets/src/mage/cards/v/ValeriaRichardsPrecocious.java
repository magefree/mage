package mage.cards.v;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ValeriaRichardsPrecocious extends CardImpl {

    private static final FilterCard filter = new FilterCard("Noncreature spells");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public ValeriaRichardsPrecocious(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Noncreature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast your first noncreature spell each turn, draw a card.
        this.addAbility(new ValeriaRichardsPrecociousTriggeredAbility());
    }

    private ValeriaRichardsPrecocious(final ValeriaRichardsPrecocious card) {
        super(card);
    }

    @Override
    public ValeriaRichardsPrecocious copy() {
        return new ValeriaRichardsPrecocious(this);
    }
}

class ValeriaRichardsPrecociousTriggeredAbility extends TriggeredAbilityImpl {

    ValeriaRichardsPrecociousTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        setTriggerPhrase("Whenever you cast your first noncreature spell each turn, ");
    }

    private ValeriaRichardsPrecociousTriggeredAbility(final ValeriaRichardsPrecociousTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ValeriaRichardsPrecociousTriggeredAbility copy() {
        return new ValeriaRichardsPrecociousTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        List<Spell> nonCreatureSpells = watcher
                .getSpellsCastThisTurn(this.getControllerId())
                .stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.isCreature(game))
                .collect(Collectors.toList());
        return nonCreatureSpells.size() == 1 && nonCreatureSpells.get(0).getId().equals(event.getTargetId());
    }
}
