package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FieryEncore extends CardImpl {

    public FieryEncore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Discard a card, then draw a card. When you discard a nonland card this way, Fiery Encore deals damage equal to that card's mana value to target creature or planeswalker.
        this.getSpellAbility().addEffect(new FieryEncoreEffect());

        // Storm
        this.addAbility(new StormAbility());
    }

    private FieryEncore(final FieryEncore card) {
        super(card);
    }

    @Override
    public FieryEncore copy() {
        return new FieryEncore(this);
    }
}

class FieryEncoreEffect extends OneShotEffect {

    FieryEncoreEffect() {
        super(Outcome.Benefit);
        staticText = "discard a card, then draw a card. When you discard a nonland card this way, " +
                "{this} deals damage equal to that card's mana value to target creature or planeswalker";
    }

    private FieryEncoreEffect(final FieryEncoreEffect effect) {
        super(effect);
    }

    @Override
    public FieryEncoreEffect copy() {
        return new FieryEncoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.discardOne(false, false, source, game);
        player.drawCards(1, source, game);
        if (card == null || card.isLand(game)) {
            return true;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(card.getManaValue()), false, "when you discard a nonland " +
                "card this way, {this} deals damage equal to that card's mana value to target creature or planeswalker"
        );
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
