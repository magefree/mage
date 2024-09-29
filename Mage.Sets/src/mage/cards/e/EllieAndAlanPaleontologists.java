package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class EllieAndAlanPaleontologists extends CardImpl {

    public EllieAndAlanPaleontologists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // {T}, Exile a creature card from your graveyard: Discover X, where X is the mana value of the exiled card. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new EllieAndAlanDiscoverEffect(), new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD), true));
        this.addAbility(ability);
    }

    private EllieAndAlanPaleontologists(final EllieAndAlanPaleontologists card) {
        super(card);
    }

    @Override
    public EllieAndAlanPaleontologists copy() {
        return new EllieAndAlanPaleontologists(this);
    }
}

class EllieAndAlanDiscoverEffect extends OneShotEffect {

    EllieAndAlanDiscoverEffect() {
        super(Outcome.Benefit);
        staticText = "discover X, where X is the mana value of the exiled card";
    }

    private EllieAndAlanDiscoverEffect(final EllieAndAlanDiscoverEffect effect) {
        super(effect);
    }

    @Override
    public EllieAndAlanDiscoverEffect copy() {
        return new EllieAndAlanDiscoverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || card == null) {
            return false;
        }
        DiscoverEffect.doDiscover(player, card.getManaValue(), game, source);
        return true;
    }
}
