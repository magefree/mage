package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCounterEnteringCreatureEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AnimalAttendant extends CardImpl {

    public AnimalAttendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add one mana of any color. If that mana is spent to cast a non-Human creature spell, that creature enters with an additional +1/+1 counter on it.
        Ability ability = new AnyColorManaAbility(new TapSourceCost(), true);
        ability.getEffects().get(0).setText("Add one mana of any color. If that mana is spent to cast a non-Human creature spell, that creature enters with an additional +1/+1 counter on it");
        this.addAbility(ability, new AnimalAttendantWatcher());
    }

    private AnimalAttendant(final AnimalAttendant card) {
        super(card);
    }

    @Override
    public AnimalAttendant copy() {
        return new AnimalAttendant(this);
    }
}
class AnimalAttendantWatcher extends Watcher {

    AnimalAttendantWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            Spell target = game.getSpell(event.getTargetId());
            if (event.getSourceId() != null
                    && event.getSourceId().equals(this.getSourceId())
                    && target != null && target.isCreature(game) && !target.hasSubtype(SubType.HUMAN, game)
                    && event.getFlag()) {
                    game.getState().addEffect(new AddCounterEnteringCreatureEffect(new MageObjectReference(target.getCard(), game)),
                        target.getSpellAbility());
            }
        }
    }
}
