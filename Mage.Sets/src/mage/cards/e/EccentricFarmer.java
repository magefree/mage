package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EccentricFarmer extends CardImpl {

    public EccentricFarmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Eccentric Farmer enters the battlefield, mill three cards, then you may return a land card from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EccentricFarmerEffect()));
    }

    private EccentricFarmer(final EccentricFarmer card) {
        super(card);
    }

    @Override
    public EccentricFarmer copy() {
        return new EccentricFarmer(this);
    }
}

class EccentricFarmerEffect extends OneShotEffect {

    EccentricFarmerEffect() {
        super(Outcome.Benefit);
        staticText = "mill three cards, then you may return a land card from your graveyard to your hand";
    }

    private EccentricFarmerEffect(final EccentricFarmerEffect effect) {
        super(effect);
    }

    @Override
    public EccentricFarmerEffect copy() {
        return new EccentricFarmerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.millCards(3, source, game);
        if (player.getGraveyard().count(StaticFilters.FILTER_CARD_LAND, game) < 1) {
            return true;
        }
        TargetCard target = new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_LAND, true
        );
        player.choose(outcome, target, source, game);
        player.moveCards(game.getCard(target.getFirstTarget()), Zone.HAND, source, game);
        return true;
    }
}
