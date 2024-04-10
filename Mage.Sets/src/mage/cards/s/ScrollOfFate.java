package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScrollOfFate extends CardImpl {

    public ScrollOfFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Manifest a card from your hand.
        this.addAbility(new SimpleActivatedAbility(new ScrollOfFateEffect(),
                new TapSourceCost()));
    }

    private ScrollOfFate(final ScrollOfFate card) {
        super(card);
    }

    @Override
    public ScrollOfFate copy() {
        return new ScrollOfFate(this);
    }
}

class ScrollOfFateEffect extends OneShotEffect {

    ScrollOfFateEffect() {
        super(Outcome.Benefit);
        staticText = "manifest a card from your hand";
    }

    private ScrollOfFateEffect(final ScrollOfFateEffect effect) {
        super(effect);
    }

    @Override
    public ScrollOfFateEffect copy() {
        return new ScrollOfFateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        TargetCardInHand targetCard = new TargetCardInHand();
        if (!controller.chooseTarget(Outcome.PutCardInPlay, controller.getHand(), targetCard, source, game)) {
            return false;
        }

        return ManifestEffect.doManifestCards(game, source, controller, new CardsImpl(targetCard.getTargets()).getCards(game));
    }
}
