package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrimevalSpawn extends CardImpl {

    public PrimevalSpawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{U}{B}{R}{G}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // If Primeval Spawn would enter the battlefield and it wasn't cast or no mana was spent to cast it, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new PrimevalSpawnReplacementEffect()));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Primeval Spawn leaves the battlefield, exile the top ten cards of your library. You may cast any number of spells with total mana value 10 or less from among them without paying their mana costs.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new PrimevalSpawnSpellEffect(), false));
    }

    private PrimevalSpawn(final PrimevalSpawn card) {
        super(card);
    }

    @Override
    public PrimevalSpawn copy() {
        return new PrimevalSpawn(this);
    }
}

class PrimevalSpawnReplacementEffect extends ReplacementEffectImpl {

    public PrimevalSpawnReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "if {this} would enter the battlefield and " +
                "it wasn't cast or no mana was spent to cast it, exile it instead";
    }

    public PrimevalSpawnReplacementEffect(final PrimevalSpawnReplacementEffect effect) {
        super(effect);
    }

    @Override
    public PrimevalSpawnReplacementEffect copy() {
        return new PrimevalSpawnReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(event.getTargetId());
        if (card != null) {
            controller.moveCards(card, Zone.EXILED, source, game);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!source.getSourceId().equals(event.getTargetId())) {
            return false;
        }
        if (((EntersTheBattlefieldEvent) event).getFromZone() != Zone.STACK) {
            return true;
        }
        Spell spell = game.getSpell(event.getTargetId());
        return spell != null && spell.getStackAbility().getManaCostsToPay().getUsedManaToPay().count() < 1;
    }
}

class PrimevalSpawnSpellEffect extends OneShotEffect {

    PrimevalSpawnSpellEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top ten cards of your library. You may cast any number of spells " +
                "with total mana value 10 or less from among them without paying their mana costs";
    }

    private PrimevalSpawnSpellEffect(final PrimevalSpawnSpellEffect effect) {
        super(effect);
    }

    @Override
    public PrimevalSpawnSpellEffect copy() {
        return new PrimevalSpawnSpellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 10));
        player.moveCards(cards, Zone.EXILED, source, game);
        CardUtil.castMultipleWithAttributeForFree(
                player, source, game, cards, StaticFilters.FILTER_CARD,
                Integer.MAX_VALUE, new PrimevalSpawnTracker()
        );
        return true;
    }
}

class PrimevalSpawnTracker implements CardUtil.SpellCastTracker {

    private int totalManaValue = 0;

    @Override
    public boolean checkCard(Card card, Game game) {
        return card.getManaValue() + totalManaValue <= 10;
    }

    @Override
    public void addCard(Card card, Ability source, Game game) {
        totalManaValue += card.getManaValue();
    }
}
