package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.BecomeDayAsEntersAbility;
import mage.abilities.common.BecomesDayOrNightTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheCelestus extends CardImpl {

    public TheCelestus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.addSuperType(SuperType.LEGENDARY);

        // If it's neither day nor night, it becomes day as The Celestus enters the battlefield.
        this.addAbility(new BecomeDayAsEntersAbility());

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {3}, {T}: If it's night, it becomes day. Otherwise, it becomes night. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new TheCelestusDayNightEffect(), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Whenever day becomes night or night becomes day, you gain 1 life. You may draw a card. If you do, discard a card.
        this.addAbility(new BecomesDayOrNightTriggeredAbility(new TheCelestusLootEffect()));
    }

    private TheCelestus(final TheCelestus card) {
        super(card);
    }

    @Override
    public TheCelestus copy() {
        return new TheCelestus(this);
    }
}

class TheCelestusDayNightEffect extends OneShotEffect {

    TheCelestusDayNightEffect() {
        super(Outcome.Benefit);
        staticText = "if it's night, it becomes day. Otherwise, it becomes night";
    }

    private TheCelestusDayNightEffect(final TheCelestusDayNightEffect effect) {
        super(effect);
    }

    @Override
    public TheCelestusDayNightEffect copy() {
        return new TheCelestusDayNightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.checkDayNight(false)) {
            game.setDaytime(true);
        } else {
            game.setDaytime(false);
        }
        return true;
    }
}

class TheCelestusLootEffect extends OneShotEffect {

    TheCelestusLootEffect() {
        super(Outcome.DrawCard);
        staticText = "you gain 1 life. You may draw a card. If you do, discard a card";
    }

    private TheCelestusLootEffect(final TheCelestusLootEffect effect) {
        super(effect);
    }

    @Override
    public TheCelestusLootEffect copy() {
        return new TheCelestusLootEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.gainLife(1, game, source);
        if (player.chooseUse(outcome, "Draw a card?", source, game)) {
            player.drawCards(1, source, game);
            player.discard(1, false, false, source, game);
        }
        return true;
    }
}
