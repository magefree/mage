package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author North
 */
public final class HauntedFengraf extends CardImpl {

    public HauntedFengraf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {tap}, Sacrifice Haunted Fengraf: Return a creature card at random from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(new HauntedFengrafEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private HauntedFengraf(final HauntedFengraf card) {
        super(card);
    }

    @Override
    public HauntedFengraf copy() {
        return new HauntedFengraf(this);
    }
}

class HauntedFengrafEffect extends OneShotEffect {

    HauntedFengrafEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return a creature card at random from your graveyard to your hand";
    }

    private HauntedFengrafEffect(final HauntedFengrafEffect effect) {
        super(effect);
    }

    @Override
    public HauntedFengrafEffect copy() {
        return new HauntedFengrafEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE);
        target.setNotTarget(true);
        target.setRandom(true);
        target.chooseTarget(outcome, player.getId(), source, game);
        return player.moveCards(game.getCard(target.getFirstTarget()), Zone.HAND, source, game);
    }
}
