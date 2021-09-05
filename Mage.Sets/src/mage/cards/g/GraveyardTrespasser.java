package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraveyardTrespasser extends CardImpl {

    public GraveyardTrespasser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.transformable = true;
        this.secondSideCardClazz = mage.cards.g.GraveyardGlutton.class;

        // Ward—Discard a card.
        this.addAbility(new WardAbility(new DiscardCardCost()));

        // Whenever Graveyard Trespasser enters the battlefield or attacks, exile up to one target card from a graveyard. If a creature card was exiled this way, each opponent loses 1 life and you gain 1 life.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GraveyardTrespasserEffect());
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        this.addAbility(ability);

        // Daybound
        this.addAbility(new TransformAbility());
        this.addAbility(DayboundAbility.getInstance());
    }

    private GraveyardTrespasser(final GraveyardTrespasser card) {
        super(card);
    }

    @Override
    public GraveyardTrespasser copy() {
        return new GraveyardTrespasser(this);
    }
}

class GraveyardTrespasserEffect extends OneShotEffect {

    GraveyardTrespasserEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target card from a graveyard. " +
                "If a creature card was exiled this way, each opponent loses 1 life and you gain 1 life";
    }

    private GraveyardTrespasserEffect(final GraveyardTrespasserEffect effect) {
        super(effect);
    }

    @Override
    public GraveyardTrespasserEffect copy() {
        return new GraveyardTrespasserEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        if (player == null || cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        int amount = cards.count(StaticFilters.FILTER_CARD_CREATURE, game);
        if (amount < 1) {
            return true;
        }
        player.gainLife(amount, game, source);
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.loseLife(amount, game, source, false);
            }
        }
        return true;
    }
}
