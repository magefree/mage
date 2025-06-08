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
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrzasTome extends CardImpl {

    private static final FilterCard filter = new FilterCard("historic card");
    static {
        filter.add(HistoricPredicate.instance);
    }

    public UrzasTome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {3}, {T}: Draw a card. Then discard a card unless you exile a historic card from your graveyard.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(3));
        ability.addEffect(new UrzasTomeEffect());
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

    private static final FilterCard filter = new FilterCard("a historic card");
    static {
        filter.add(HistoricPredicate.instance);
    }

    UrzasTomeEffect() {
        super(Outcome.Discard);
        staticText = "Then discard a card unless you exile a historic card from your graveyard";
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
        if (controller == null) {
            return false;
        }
        if (controller.chooseUse(Outcome.Exile, "Exile a historic card from your graveyard?", source, game)) {
            Cost cost = new ExileFromGraveCost(new TargetCardInYourGraveyard(filter));
            if (cost.canPay(source, source, controller.getId(), game)
                    && cost.pay(source, game, source, controller.getId(), false, null)) {
                return true;
            }
        }
        controller.discard(1, false, false, source, game);
        return true;
    }
}
