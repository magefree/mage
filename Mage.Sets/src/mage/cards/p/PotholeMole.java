package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.Card;
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
public final class PotholeMole extends CardImpl {

    public PotholeMole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.MOLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When this creature enters, mill three cards, then you may return a land card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3));
        ability.addEffect(new PotholeMoleEffect());
        this.addAbility(ability);
    }

    private PotholeMole(final PotholeMole card) {
        super(card);
    }

    @Override
    public PotholeMole copy() {
        return new PotholeMole(this);
    }
}

class PotholeMoleEffect extends OneShotEffect {

    PotholeMoleEffect() {
        super(Outcome.Benefit);
        staticText = ", then you may return a land card from your graveyard to your hand";
    }

    private PotholeMoleEffect(final PotholeMoleEffect effect) {
        super(effect);
    }

    @Override
    public PotholeMoleEffect copy() {
        return new PotholeMoleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_LAND, true
        );
        player.choose(outcome, player.getGraveyard(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }
}
