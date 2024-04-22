package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetDiscard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author Xanderhall
 */
public final class LoamcrafterFaun extends CardImpl {

    public LoamcrafterFaun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Loamcrafter Faun enters the battlefield, you may discard one or more land cards. When you do, return up to that many target nonland permanent cards from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LoamcrafterFaunDiscardEffect()));
    }

    private LoamcrafterFaun(final LoamcrafterFaun card) {
        super(card);
    }

    @Override
    public LoamcrafterFaun copy() {
        return new LoamcrafterFaun(this);
    }
}

class LoamcrafterFaunDiscardEffect extends OneShotEffect {

    private static FilterCard filter = new FilterPermanentCard("nonland permanent cards in your graveyard");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    LoamcrafterFaunDiscardEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may discard one or more land cards. When you do, return up to that many target nonland permanent cards from your graveyard to your hand.";
    }

    private LoamcrafterFaunDiscardEffect(final LoamcrafterFaunDiscardEffect effect) {
        super(effect);
    }

    @Override
    public LoamcrafterFaunDiscardEffect copy() {
        return new LoamcrafterFaunDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetDiscard target = new TargetDiscard(
                0, Integer.MAX_VALUE,
                StaticFilters.FILTER_CARD_LANDS, player.getId()
        );
        player.choose(Outcome.Discard, target, source, game);
        Cards cards = player.discard(new CardsImpl(target.getTargets()), false, source, game);

        // No cards discarded, effect still resolves
        if (cards.isEmpty()) {
            return true;
        }

        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(0, cards.size(), filter, false));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }

}