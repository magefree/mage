package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetImpl;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SparringDummy extends CardImpl {

    public SparringDummy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {T}: Mill a card. You may put a land card milled this way into your hand. You gain 2 life if a Lesson card is milled this way.
        this.addAbility(new SimpleActivatedAbility(new SparringDummyEffect(), new TapSourceCost()));
    }

    private SparringDummy(final SparringDummy card) {
        super(card);
    }

    @Override
    public SparringDummy copy() {
        return new SparringDummy(this);
    }
}

class SparringDummyEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard(SubType.LESSON);

    SparringDummyEffect() {
        super(Outcome.Benefit);
        staticText = "mill a card. You may put a land card milled this way into your hand. " +
                "You gain 2 life if a Lesson card is milled this way";
    }

    private SparringDummyEffect(final SparringDummyEffect effect) {
        super(effect);
    }

    @Override
    public SparringDummyEffect copy() {
        return new SparringDummyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(1, source, game);
        TargetCard target = new TargetCard(0, 1, Zone.ALL, StaticFilters.FILTER_CARD_LAND);
        player.choose(Outcome.DrawCard, cards, target, source, game);
        Optional.ofNullable(target)
                .map(TargetImpl::getFirstTarget)
                .map(game::getCard)
                .ifPresent(card -> player.moveCards(card, Zone.HAND, source, game));
        if (cards.count(filter, game) > 0) {
            player.gainLife(2, game, source);
        }
        return true;
    }
}
