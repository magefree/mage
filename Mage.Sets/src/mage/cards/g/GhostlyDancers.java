package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.Spirit31Token;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhostlyDancers extends CardImpl {

    public GhostlyDancers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, return an enchantment card from your graveyard to your hand or unlock a locked door of a Room you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GhostlyDancersEffect()));

        // Eerie -- Whenever an enchantment you control enters and whenever you fully unlock a Room, create a 3/1 white Spirit creature token with flying.
        this.addAbility(new EerieAbility(new CreateTokenEffect(new Spirit31Token())));
    }

    private GhostlyDancers(final GhostlyDancers card) {
        super(card);
    }

    @Override
    public GhostlyDancers copy() {
        return new GhostlyDancers(this);
    }
}

class GhostlyDancersEffect extends OneShotEffect {

    GhostlyDancersEffect() {
        super(Outcome.Benefit);
        staticText = "return an enchantment card from your graveyard to your hand or unlock a locked door of a Room you control";
    }

    private GhostlyDancersEffect(final GhostlyDancersEffect effect) {
        super(effect);
    }

    @Override
    public GhostlyDancersEffect copy() {
        return new GhostlyDancersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // TODO: 7/7/25 this needs to be refactored when rooms are implemented
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(StaticFilters.FILTER_CARD_ENCHANTMENT, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ENCHANTMENT);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }
}
