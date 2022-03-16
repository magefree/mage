package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.costs.costadjusters.LegendaryCreatureCostAdjuster;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TakenumaAbandonedMire extends CardImpl {

    public TakenumaAbandonedMire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.addSuperType(SuperType.LEGENDARY);

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // Channel — {3}{B}, Discard Takenuma, Abandoned Mire: Mill three cards, then return a creature or planeswalker card from your graveyard to your hand. This ability costs {1} less to activate for each legendary creature you control.
        Ability ability = new ChannelAbility("{3}{B}", new TakenumaAbandonedMireEffect());
        ability.setCostAdjuster(LegendaryCreatureCostAdjuster.instance);
        this.addAbility(ability);
    }

    private TakenumaAbandonedMire(final TakenumaAbandonedMire card) {
        super(card);
    }

    @Override
    public TakenumaAbandonedMire copy() {
        return new TakenumaAbandonedMire(this);
    }
}

class TakenumaAbandonedMireEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature or planeswalker card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    TakenumaAbandonedMireEffect() {
        super(Outcome.Benefit);
        staticText = "mill three cards, then return a creature or planeswalker card from your graveyard to your hand. " +
                "This ability costs {1} less to activate for each legendary creature you control";
    }

    private TakenumaAbandonedMireEffect(final TakenumaAbandonedMireEffect effect) {
        super(effect);
    }

    @Override
    public TakenumaAbandonedMireEffect copy() {
        return new TakenumaAbandonedMireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.millCards(3, source, game);
        if (player.getGraveyard().count(filter, game) < 1) {
            return true;
        }
        TargetCard target = new TargetCardInGraveyard(filter);
        target.setNotTarget(true);
        player.choose(outcome, player.getGraveyard(), target, game);
        return player.moveCards(game.getCard(target.getFirstTarget()), Zone.HAND, source, game);
    }
}
