package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChaosShrinesBlackCrystal extends CardImpl {

    public ChaosShrinesBlackCrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever a nontoken creature you control dies, exile it.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new ExileTargetForSourceEffect(), false,
                StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN, true
        ));

        // At the beginning of your upkeep, you may put a creature card exiled with Chaos Shrine's Black Crystal onto the battlefield under your control with a finality counter on it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ChaosShrinesBlackCrystalEffect()));
    }

    private ChaosShrinesBlackCrystal(final ChaosShrinesBlackCrystal card) {
        super(card);
    }

    @Override
    public ChaosShrinesBlackCrystal copy() {
        return new ChaosShrinesBlackCrystal(this);
    }
}

class ChaosShrinesBlackCrystalEffect extends OneShotEffect {

    ChaosShrinesBlackCrystalEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a creature card exiled with {this} " +
                "onto the battlefield under your control with a finality counter on it";
    }

    private ChaosShrinesBlackCrystalEffect(final ChaosShrinesBlackCrystalEffect effect) {
        super(effect);
    }

    @Override
    public ChaosShrinesBlackCrystalEffect copy() {
        return new ChaosShrinesBlackCrystalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (player == null || exileZone == null) {
            return false;
        }
        TargetCard target = new TargetCardInExile(
                0, 1, StaticFilters.FILTER_CARD_CREATURE, exileZone.getId()
        );
        player.choose(Outcome.PutCreatureInPlay, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        game.setEnterWithCounters(card.getId(), new Counters(CounterType.FINALITY.createInstance()));
        return player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
