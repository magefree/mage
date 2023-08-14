package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeldonRonomExcavator extends CardImpl {

    public FeldonRonomExcavator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Feldon, Ronom Excavator can't block.
        this.addAbility(new CantBlockAbility());

        // Whenever Feldon is dealt damage, exile that many cards from the top of your library. Choose one of them. Until the end of your next turn, you may play that card.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new FeldonRonomExcavatorEffect(), false));
    }

    private FeldonRonomExcavator(final FeldonRonomExcavator card) {
        super(card);
    }

    @Override
    public FeldonRonomExcavator copy() {
        return new FeldonRonomExcavator(this);
    }
}

class FeldonRonomExcavatorEffect extends OneShotEffect {

    FeldonRonomExcavatorEffect() {
        super(Outcome.Benefit);
        staticText = "exile that many cards from the top of your library. " +
                "Choose one of them. Until the end of your next turn, you may play that card";
    }

    private FeldonRonomExcavatorEffect(final FeldonRonomExcavatorEffect effect) {
        super(effect);
    }

    @Override
    public FeldonRonomExcavatorEffect copy() {
        return new FeldonRonomExcavatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int damage = (Integer) getValue("damage");
        if (player == null || damage < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, damage));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        Card card;
        switch (cards.size()) {
            case 0:
                return false;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD);
                target.setNotTarget(true);
                player.choose(outcome, cards, target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        if (card != null) {
            CardUtil.makeCardPlayable(game, source, card, Duration.UntilEndOfYourNextTurn, false);
        }
        return true;
    }
}
