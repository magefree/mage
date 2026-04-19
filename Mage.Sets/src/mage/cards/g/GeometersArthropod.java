package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class GeometersArthropod extends CardImpl {

    static final String needPrefix = "_GeometersArthropod_NeedSpell";

    public GeometersArthropod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.FRACTAL);
        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever you cast a spell with {X} in its mana cost, look at the top X cards of your library. Put one of them into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new GeometersArthropodAbility());
    }

    private GeometersArthropod(final GeometersArthropod card) {
        super(card);
    }

    @Override
    public GeometersArthropod copy() {
        return new GeometersArthropod(this);
    }
}

class GeometersArthropodAbility extends TriggeredAbilityImpl {

    public GeometersArthropodAbility() {
        super(Zone.BATTLEFIELD, new GeometersArthropodEffect(), false);
        setTriggerPhrase("Whenever you cast a spell with {X} in its mana cost");
    }

    private GeometersArthropodAbility(final GeometersArthropodAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId()) || event.getType() != GameEvent.EventType.SPELL_CAST) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || !spell.getSpellAbility().getManaCostsToPay().containsX()) {
            return false;
        }
        game.getState().setValue(this.getSourceId() + GeometersArthropod.needPrefix, spell);
        return true;
    }

    @Override
    public GeometersArthropodAbility copy() {
        return new GeometersArthropodAbility(this);
    }
}

class GeometersArthropodEffect extends OneShotEffect {
    GeometersArthropodEffect() {
        super(Outcome.Benefit);
        this.staticText = ", look at the top X cards of your library. "
          + "Put one of them into your hand and the rest on the bottom "
          + "of your library in a random order.";
    }

    private GeometersArthropodEffect(final GeometersArthropodEffect effect) {
        super(effect);
    }

    @Override
    public GeometersArthropodEffect copy() {
        return new GeometersArthropodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Object needObject = game.getState().getValue(source.getSourceId() + GeometersArthropod.needPrefix);
        if (!(needObject instanceof Spell)) {
            return false;
        }

        Spell spell = (Spell) needObject;
        int xValue = CardUtil.getSourceCostsTag(game, spell.getSpellAbility(), "X", 0);
        if (xValue < 1) {
            return true;
        }

        return new LookLibraryAndPickControllerEffect(
            StaticValue.get(xValue), 1, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ).apply(game, source);
    }
}
