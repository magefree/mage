package mage.cards.j;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class JelevaNephaliasScourge extends CardImpl {

    public JelevaNephaliasScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Jeleva, Nephalia's Scourge enters the battlefield, each player exiles 
        // the top X cards of their library, where X is the amount of mana spent to cast Jeleva.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new JelevaNephaliasScourgeEffect(), false));

        // Whenever Jeleva attacks, you may cast an instant or sorcery card exiled with it without paying its mana cost.
        this.addAbility(new AttacksTriggeredAbility(new JelevaNephaliasCastEffect(), false), new JelevaNephaliasWatcher());

    }

    private JelevaNephaliasScourge(final JelevaNephaliasScourge card) {
        super(card);
    }

    @Override
    public JelevaNephaliasScourge copy() {
        return new JelevaNephaliasScourge(this);
    }
}

class JelevaNephaliasScourgeEffect extends OneShotEffect {

    public JelevaNephaliasScourgeEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player exiles the top X cards of their library, "
                + "where X is the amount of mana spent to cast this spell";
    }

    public JelevaNephaliasScourgeEffect(final JelevaNephaliasScourgeEffect effect) {
        super(effect);
    }

    @Override
    public JelevaNephaliasScourgeEffect copy() {
        return new JelevaNephaliasScourgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        JelevaNephaliasWatcher watcher = game.getState().getWatcher(JelevaNephaliasWatcher.class);
        if (controller != null
                && permanent != null
                && watcher != null) {
            int xValue = watcher.getManaSpentToCastLastTime(permanent.getId(), permanent.getZoneChangeCounter(game) - 1);
            if (xValue > 0) {
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.moveCardsToExile(player.getLibrary().getTopCards(game, xValue),
                                source, game, true, CardUtil.getCardExileZoneId(game, source), permanent.getIdName());
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class JelevaNephaliasCastEffect extends OneShotEffect {

    public JelevaNephaliasCastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast an instant or sorcery spell " +
                "from among cards exiled with {this} without paying its mana cost";
    }

    public JelevaNephaliasCastEffect(final JelevaNephaliasCastEffect effect) {
        super(effect);
    }

    @Override
    public JelevaNephaliasCastEffect copy() {
        return new JelevaNephaliasCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source)));
        return CardUtil.castSpellWithAttributesForFree(
                controller, source, game, cards,
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY
        );
    }
}

class JelevaNephaliasWatcher extends Watcher {

    private final Map<String, Integer> manaSpendToCast = new HashMap<>(); // cast

    public JelevaNephaliasWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        // Watcher saves all casts becaus of possible Clone cards that copy Jeleva
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            if (!game.getStack().isEmpty()) {
                for (StackObject stackObject : game.getStack()) {
                    if (stackObject instanceof Spell) {
                        Spell spell = (Spell) stackObject;
                        manaSpendToCast.putIfAbsent(spell.getSourceId().toString()
                                        + spell.getCard().getZoneChangeCounter(game),
                                spell.getSpellAbility().getManaCostsToPay().manaValue());
                    }
                }
            }
        }
    }

    public int getManaSpentToCastLastTime(UUID sourceId, int zoneChangeCounter) {
        return manaSpendToCast.getOrDefault(sourceId.toString() + zoneChangeCounter, 0);
    }

    @Override
    public void reset() {
        super.reset();
        manaSpendToCast.clear();
    }
}
