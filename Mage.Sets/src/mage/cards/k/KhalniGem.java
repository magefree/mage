package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class KhalniGem extends CardImpl {

    public KhalniGem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // When Khalni Gem enters the battlefield, return two lands you control to their owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KhalniGemReturnToHandTargetEffect()));

        // {tap}: Add two mana of any one color.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost()
        ));
    }

    private KhalniGem(final KhalniGem card) {
        super(card);
    }

    @Override
    public KhalniGem copy() {
        return new KhalniGem(this);
    }
}

class KhalniGemReturnToHandTargetEffect extends OneShotEffect {

    private static final String effectText = "return two lands you control to their owner's hand";

    KhalniGemReturnToHandTargetEffect() {
        super(Outcome.ReturnToHand);
        staticText = effectText;
    }

    private KhalniGemReturnToHandTargetEffect(KhalniGemReturnToHandTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int landCount = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                source.getControllerId(), source, game
        );
        if (player == null || landCount < 1) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                Math.min(landCount, 2), StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND
        );
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        return player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
    }

    @Override
    public KhalniGemReturnToHandTargetEffect copy() {
        return new KhalniGemReturnToHandTargetEffect(this);
    }
}
