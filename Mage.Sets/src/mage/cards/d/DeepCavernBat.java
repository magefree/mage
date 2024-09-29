package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DeepCavernBat extends CardImpl {

    public DeepCavernBat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.BAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Deep-Cavern Bat enters the battlefield, look at target opponent's hand. You may exile a nonland card from it until Deep-Cavern Bat leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DeepCaverBatEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private DeepCavernBat(final DeepCavernBat card) {
        super(card);
    }

    @Override
    public DeepCavernBat copy() {
        return new DeepCavernBat(this);
    }
}

class DeepCaverBatEffect extends OneShotEffect {

    DeepCaverBatEffect() {
        super(Outcome.Exile);
        staticText = "look at target opponent's hand. You may exile a nonland card from it until {this} leaves the battlefield";
    }

    private DeepCaverBatEffect(final DeepCaverBatEffect effect) {
        super(effect);
    }

    @Override
    public DeepCaverBatEffect copy() {
        return new DeepCaverBatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null || opponent.getHand().isEmpty()) {
            return false;
        }

        MageObject sourceObj = source.getSourceObject(game);
        // Looking at the opponent's hand without check on if the bat is still on the battlefield.
        controller.lookAtCards(sourceObj == null ? null : sourceObj.getIdName(), opponent.getHand(), game);

        Permanent bat = source.getSourcePermanentIfItStillExists(game);
        if (bat == null) {
            return true;
        }

        TargetCard target = new TargetCardInHand(
                0, 1, StaticFilters.FILTER_CARD_A_NON_LAND
        );
        controller.choose(outcome, opponent.getHand(), target, source, game);
        Card card = opponent.getHand().get(target.getFirstTarget(), game);
        if (card == null) {
            return true;
        }
        Cards cards = new CardsImpl(target.getTargets());
        controller.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                bat.getIdName()
        );
        game.addDelayedTriggeredAbility(new OnLeaveReturnExiledAbility(Zone.HAND), source);
        return true;
    }

}