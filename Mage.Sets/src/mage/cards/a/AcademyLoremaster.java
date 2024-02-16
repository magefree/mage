package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AcademyLoremaster extends CardImpl {

    public AcademyLoremaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of each player's draw step, that player may draw an additional card. If they do, spells they cast this turn cost {2} more to cast.
        this.addAbility(new BeginningOfDrawTriggeredAbility(
                new AcademyLoremasterEffect(), TargetController.ANY, false
        ));
    }

    private AcademyLoremaster(final AcademyLoremaster card) {
        super(card);
    }

    @Override
    public AcademyLoremaster copy() {
        return new AcademyLoremaster(this);
    }
}

class AcademyLoremasterEffect extends OneShotEffect {

    AcademyLoremasterEffect() {
        super(Outcome.Benefit);
        staticText = "that player may draw an additional card. " +
                "If they do, spells they cast this turn cost {2} more to cast";
    }

    private AcademyLoremasterEffect(final AcademyLoremasterEffect effect) {
        super(effect);
    }

    @Override
    public AcademyLoremasterEffect copy() {
        return new AcademyLoremasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player == null
                || !player.chooseUse(outcome, "Draw an additional card?", source, game)
                || player.drawCards(1, source, game) < 1) {
            return false;
        }
        game.addEffect(new SpellsCostIncreasingAllEffect(
                2, StaticFilters.FILTER_CARD, TargetController.ACTIVE
        ).setDuration(Duration.EndOfTurn), source);
        return true;
    }
}
