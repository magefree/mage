package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CultivatorColossus extends CardImpl {

    public CultivatorColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cultivator Colossus's power and toughness are each equal to the number of lands you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetPowerToughnessSourceEffect(LandsYouControlCount.instance, Duration.EndOfGame)
        ));

        // When Cultivator Colossus enters the battlefield, you may put a land card from your hand onto the battlefield tapped. If you do, draw a card and repeat this process.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CultivatorColossusEffect()));
    }

    private CultivatorColossus(final CultivatorColossus card) {
        super(card);
    }

    @Override
    public CultivatorColossus copy() {
        return new CultivatorColossus(this);
    }
}

class CultivatorColossusEffect extends OneShotEffect {

    CultivatorColossusEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "you may put a land card from your hand onto the battlefield tapped. " +
                "If you do, draw a card and repeat this process";
    }

    private CultivatorColossusEffect(final CultivatorColossusEffect effect) {
        super(effect);
    }

    @Override
    public CultivatorColossusEffect copy() {
        return new CultivatorColossusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        while (player.getHand().count(StaticFilters.FILTER_CARD_LAND, game) > 0) {
            TargetCard target = new TargetCardInHand(
                    0, 1, StaticFilters.FILTER_CARD_LAND
            );
            player.choose(outcome, target, source.getSourceId(), game);
            Card card = game.getCard(target.getFirstTarget());
            if (card == null) {
                break;
            }
            player.moveCards(
                    card, Zone.BATTLEFIELD, source, game, true,
                    false, false, null
            );
            if (game.getPermanent(card.getId()) == null) {
                break;
            }
            player.drawCards(1, source, game);
        }
        return true;
    }
}
