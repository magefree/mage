package mage.cards.g;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCounterEnteringCreatureEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class GuildmagesForum extends CardImpl {

    public GuildmagesForum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Add one mana of any color. If that mana is spent on a multicolored creature spell, that creature enters the battlefield with an additional +1/+1 counter on it.
        Ability ability = new AnyColorManaAbility(new GenericManaCost(1), true);
        ability.getEffects().get(0).setText("Add one mana of any color. If that mana is spent on a multicolored creature spell, that creature enters the battlefield with an additional +1/+1 counter on it");
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new GuildmagesForumWatcher());
    }

    private GuildmagesForum(final GuildmagesForum card) {
        super(card);
    }

    @Override
    public GuildmagesForum copy() {
        return new GuildmagesForum(this);
    }
}

class GuildmagesForumWatcher extends Watcher {


    GuildmagesForumWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            Spell target = game.getSpell(event.getTargetId());
            if (event.getSourceId() != null
                    && event.getSourceId().equals(this.getSourceId())
                    && target != null && target.isCreature(game) && target.getColor(game).isMulticolored()
                    && event.getFlag()) {
                    game.getState().addEffect(new AddCounterEnteringCreatureEffect(new MageObjectReference(target.getCard(), game)),
                        target.getSpellAbility());
            }
        }
    }
}
