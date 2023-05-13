package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZimoneAndDina extends CardImpl {

    public ZimoneAndDina(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you draw your second card each turn, target opponent loses 2 life and you gain 2 life.
        Ability ability = new DrawNthCardTriggeredAbility(new LoseLifeTargetEffect(2), false, 2);
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {T}, Sacrifice another creature: Draw a card. You may put a land card from your hand onto the battlefield tapped. If you control eight or more lands, repeat this process once.
        ability = new SimpleActivatedAbility(new ZimoneAndDinaEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private ZimoneAndDina(final ZimoneAndDina card) {
        super(card);
    }

    @Override
    public ZimoneAndDina copy() {
        return new ZimoneAndDina(this);
    }
}

class ZimoneAndDinaEffect extends OneShotEffect {

    ZimoneAndDinaEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card. You may put a land card from your hand onto the battlefield tapped. " +
                "If you control eight or more lands, repeat this process once";
    }

    private ZimoneAndDinaEffect(final ZimoneAndDinaEffect effect) {
        super(effect);
    }

    @Override
    public ZimoneAndDinaEffect copy() {
        return new ZimoneAndDinaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        drawAndPlayLand(player, source, game);
        if (game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                source.getControllerId(), source, game
        ) >= 8) {
            drawAndPlayLand(player, source, game);
        }
        return true;
    }

    private void drawAndPlayLand(Player player, Ability source, Game game) {
        player.drawCards(1, source, game);
        TargetCard target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_LAND_A);
        player.choose(Outcome.PutCardInPlay, target, source, game);
        player.moveCards(
                game.getCard(target.getFirstTarget()), Zone.BATTLEFIELD, source,
                game, true, false, false, null
        );
    }
}
