package mage.cards.g;

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
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author North
 */
public final class Ghoulraiser extends CardImpl {

    public Ghoulraiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Ghoulraiser enters the battlefield, return a Zombie card at random from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GhoulraiserEffect(), false));
    }

    private Ghoulraiser(final Ghoulraiser card) {
        super(card);
    }

    @Override
    public Ghoulraiser copy() {
        return new Ghoulraiser(this);
    }
}

class GhoulraiserEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    GhoulraiserEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return a Zombie card at random from your graveyard to your hand";
    }

    private GhoulraiserEffect(final GhoulraiserEffect effect) {
        super(effect);
    }

    @Override
    public GhoulraiserEffect copy() {
        return new GhoulraiserEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(filter, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(filter);
        target.setNotTarget(true);
        target.setRandom(true);
        target.chooseTarget(outcome, player.getId(), source, game);
        return player.moveCards(game.getCard(target.getFirstTarget()), Zone.HAND, source, game);
    }
}
