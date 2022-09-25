package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExaltedFlamerOfTzeentch extends CardImpl {

    public ExaltedFlamerOfTzeentch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Sorcerous Inspiration -- At the beginning of your upkeep, return an instant or sorcery card at random from your graveyard to your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ExaltedFlamerOfTzeentchEffect(), TargetController.YOU, false
        ).withFlavorWord("Sorcerous Inspiration"));

        // Fire of Tzeentch -- Whenever you cast an instant or sorcery spell, Exalted Flamer of Tzeentch deals 1 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ).withFlavorWord("Fire of Tzeentch"));
    }

    private ExaltedFlamerOfTzeentch(final ExaltedFlamerOfTzeentch card) {
        super(card);
    }

    @Override
    public ExaltedFlamerOfTzeentch copy() {
        return new ExaltedFlamerOfTzeentch(this);
    }
}

class ExaltedFlamerOfTzeentchEffect extends OneShotEffect {

    ExaltedFlamerOfTzeentchEffect() {
        super(Outcome.Benefit);
        staticText = "return an instant or sorcery card at random from your graveyard to your hand";
    }

    private ExaltedFlamerOfTzeentchEffect(final ExaltedFlamerOfTzeentchEffect effect) {
        super(effect);
    }

    @Override
    public ExaltedFlamerOfTzeentchEffect copy() {
        return new ExaltedFlamerOfTzeentchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game));
        Card card = cards.getRandom(game);
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }
}
