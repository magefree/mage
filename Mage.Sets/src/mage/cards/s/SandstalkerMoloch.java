package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SandstalkerMoloch extends CardImpl {

    public SandstalkerMoloch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Sandstalker Moloch enters the battlefield, if an opponent cast a blue and/or black spell this turn, look at the top four cards of your library. You may reveal a permanent card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(
                        new LookLibraryAndPickControllerEffect(
                                4, 1, StaticFilters.FILTER_CARD_A_PERMANENT,
                                PutCards.HAND, PutCards.BOTTOM_RANDOM
                        )
                ), SandstalkerMolochWatcher::checkPlayer, "When {this} enters the battlefield, " +
                "if an opponent cast a blue and/or black spell this turn, look at the top four cards " +
                "of your library. You may reveal a permanent card from among them and put it into your hand. " +
                "Put the rest on the bottom of your library in a random order."
        ), new SandstalkerMolochWatcher());
    }

    private SandstalkerMoloch(final SandstalkerMoloch card) {
        super(card);
    }

    @Override
    public SandstalkerMoloch copy() {
        return new SandstalkerMoloch(this);
    }
}

class SandstalkerMolochWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();
    private static final ObjectColor color = new ObjectColor("UB");

    SandstalkerMolochWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell != null && spell.getColor(game).shares(color)) {
            players.addAll(game.getOpponents(spell.getControllerId()));
        }
    }

    @Override
    public void reset() {
        super.reset();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(SandstalkerMolochWatcher.class)
                .players
                .contains(source.getControllerId());
    }
}
