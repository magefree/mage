package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheMindskinner extends CardImpl {

    public TheMindskinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{U}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(10);
        this.toughness = new MageInt(1);

        // The Mindskinner can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // If a source you control would deal damage to an opponent, prevent that damage and each opponent mills that many cards.
        this.addAbility(new SimpleStaticAbility(new TheMindskinnerEffect()));
    }

    private TheMindskinner(final TheMindskinner card) {
        super(card);
    }

    @Override
    public TheMindskinner copy() {
        return new TheMindskinner(this);
    }
}

class TheMindskinnerEffect extends PreventionEffectImpl {

    TheMindskinnerEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "if a source you control would deal damage to an opponent, " +
                "prevent that damage and each opponent mills that many cards.";
    }

    private TheMindskinnerEffect(final TheMindskinnerEffect effect) {
        super(effect);
    }

    @Override
    public TheMindskinnerEffect copy() {
        return new TheMindskinnerEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = event.getAmount();
        preventDamageAction(event, source, game);
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.millCards(amount, source, game);
            }
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && source.isControlledBy(game.getControllerId(event.getSourceId()));
    }
}
