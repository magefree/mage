package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SequesteredStash extends CardImpl {

    public SequesteredStash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {R}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4},{T}, Sacrifice Sequestered Stash: Put the top five cards of your library into your graveyard. Then you may put an artifact card from your graveyard on top of your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MillCardsControllerEffect(5), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new SequesteredStashEffect());
        this.addAbility(ability);

    }

    private SequesteredStash(final SequesteredStash card) {
        super(card);
    }

    @Override
    public SequesteredStash copy() {
        return new SequesteredStash(this);
    }
}

class SequesteredStashEffect extends OneShotEffect {

    public SequesteredStashEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then you may put an artifact card from your graveyard on top of your library";
    }

    public SequesteredStashEffect(final SequesteredStashEffect effect) {
        super(effect);
    }

    @Override
    public SequesteredStashEffect copy() {
        return new SequesteredStashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD);
        target.setNotTarget(true);
        if (target.canChoose(source.getControllerId(), source, game)
                && controller.chooseUse(outcome, "Put an artifact card from your graveyard to library?", source, game)
                && controller.choose(outcome, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.LIBRARY, source, game);
            }
        }
        return true;
    }
}
