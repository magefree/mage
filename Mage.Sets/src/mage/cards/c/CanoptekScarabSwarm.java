package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.InsectColorlessToken;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CanoptekScarabSwarm extends CardImpl {

    public CanoptekScarabSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Feeder Mandibles -- When Canoptek Scarab Swarm enters the battlefield, exile target player's graveyard. For each artifact or land card exiled this way, create a 1/1 colorless Insect artifact creature token with flying.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CanoptekScarabSwarmEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability.withFlavorWord("Feeder Mandibles"));
    }

    private CanoptekScarabSwarm(final CanoptekScarabSwarm card) {
        super(card);
    }

    @Override
    public CanoptekScarabSwarm copy() {
        return new CanoptekScarabSwarm(this);
    }
}

class CanoptekScarabSwarmEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    CanoptekScarabSwarmEffect() {
        super(Outcome.Benefit);
        staticText = "exile target player's graveyard. For each artifact or land card exiled this way, " +
                "create a 1/1 colorless Insect artifact creature token with flying";
    }

    private CanoptekScarabSwarmEffect(final CanoptekScarabSwarmEffect effect) {
        super(effect);
    }

    @Override
    public CanoptekScarabSwarmEffect copy() {
        return new CanoptekScarabSwarmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        int count = player.getGraveyard().count(filter, game);
        player.moveCards(player.getGraveyard(), Zone.EXILED, source, game);
        new InsectColorlessToken().putOntoBattlefield(count, game, source);
        return true;
    }
}
