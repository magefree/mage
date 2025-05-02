package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.ZombieDruidToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TevalTheBalancedScale extends CardImpl {

    public TevalTheBalancedScale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Teval attacks, mill three cards. Then you may return a land card from your graveyard to the battlefield tapped.
        Ability ability = new AttacksTriggeredAbility(new MillCardsControllerEffect(3));
        ability.addEffect(new TevalTheBalancedScaleEffect());
        this.addAbility(ability);

        // Whenever one or more cards leave your graveyard, create a 2/2 black Zombie Druid creature token.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new CreateTokenEffect(new ZombieDruidToken())));
    }

    private TevalTheBalancedScale(final TevalTheBalancedScale card) {
        super(card);
    }

    @Override
    public TevalTheBalancedScale copy() {
        return new TevalTheBalancedScale(this);
    }
}

class TevalTheBalancedScaleEffect extends OneShotEffect {

    TevalTheBalancedScaleEffect() {
        super(Outcome.Benefit);
        staticText = "Then you may return a land card from your graveyard to the battlefield tapped";
    }

    private TevalTheBalancedScaleEffect(final TevalTheBalancedScaleEffect effect) {
        super(effect);
    }

    @Override
    public TevalTheBalancedScaleEffect copy() {
        return new TevalTheBalancedScaleEffect(this);
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
        return card != null && player.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, false, null
        );
    }
}
