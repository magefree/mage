package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.game.Game;
import mage.game.permanent.token.PowerstoneToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WreckHunter extends CardImpl {

    public WreckHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Wreck Hunter enters the battlefield, choose target player. You create a tapped Powerstone token for each nonland card in that player's graveyard that was put there from the battlefield this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new WreckHunterEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability, new CardsPutIntoGraveyardWatcher());
    }

    private WreckHunter(final WreckHunter card) {
        super(card);
    }

    @Override
    public WreckHunter copy() {
        return new WreckHunter(this);
    }
}

class WreckHunterEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterNonlandCard();

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    WreckHunterEffect() {
        super(Outcome.Benefit);
        staticText = "choose target player. You create a tapped Powerstone token for each nonland card " +
                "in that player's graveyard that was put there from the battlefield this turn";
    }

    private WreckHunterEffect(final WreckHunterEffect effect) {
        super(effect);
    }

    @Override
    public WreckHunterEffect copy() {
        return new WreckHunterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        int count = player.getGraveyard().count(filter, game);
        return count > 0 && new PowerstoneToken().putOntoBattlefield(
                count, game, source, source.getControllerId(), true, false
        );
    }
}

