package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.Card;
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
 * @author TheElk801
 */
public final class PortOfKarfell extends CardImpl {

    public PortOfKarfell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Port of Karfell enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {3}{U}{B}{B}, {T}, Sacrifice Port of Karfell: Mill four cards, then return a creature card from your graveyard to the battlefield.
        Ability ability = new SimpleActivatedAbility(new PortOfKarfellEffect(), new ManaCostsImpl<>("{3}{U}{B}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private PortOfKarfell(final PortOfKarfell card) {
        super(card);
    }

    @Override
    public PortOfKarfell copy() {
        return new PortOfKarfell(this);
    }
}

class PortOfKarfellEffect extends OneShotEffect {

    PortOfKarfellEffect() {
        super(Outcome.Benefit);
        staticText = "mill four cards, then return a creature card from your graveyard to the battlefield tapped";
    }

    private PortOfKarfellEffect(final PortOfKarfellEffect effect) {
        super(effect);
    }

    @Override
    public PortOfKarfellEffect copy() {
        return new PortOfKarfellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.millCards(4, source, game);
        TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        target.setNotTarget(true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return true;
        }
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        }
        return true;
    }
}
