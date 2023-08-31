package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterHistoricCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrzasTome extends CardImpl {

    public UrzasTome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {3}, {T}: Draw a card. Then discard a card unless you exile a historic card from your graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UrzasTomeEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private UrzasTome(final UrzasTome card) {
        super(card);
    }

    @Override
    public UrzasTome copy() {
        return new UrzasTome(this);
    }
}

class UrzasTomeEffect extends OneShotEffect {

    public UrzasTomeEffect() {
        super(Outcome.Discard);
        staticText = "Draw a card. Then discard a card unless you exile a historic card from your graveyard";
    }

    private UrzasTomeEffect(final UrzasTomeEffect effect) {
        super(effect);
    }

    @Override
    public UrzasTomeEffect copy() {
        return new UrzasTomeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        new DrawCardSourceControllerEffect(1).apply(game, source);
        if (controller != null
                && controller.chooseUse(Outcome.Exile, "Exile a historic card from your graveyard?", source, game)) {
            Cost cost = new ExileFromGraveCost(new TargetCardInYourGraveyard(new FilterHistoricCard()));
            if (cost.canPay(source, source, controller.getId(), game)) {
                if (cost.pay(source, game, source, controller.getId(), false, null)) {
                    return true;
                }
            }
        }
        if (controller != null) {
            controller.discard(1, false, false, source, game);
            return true;
        }
        return false;
    }
}
