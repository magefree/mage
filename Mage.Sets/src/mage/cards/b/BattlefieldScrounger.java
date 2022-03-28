
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public final class BattlefieldScrounger extends CardImpl {

    public BattlefieldScrounger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.CENTAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Threshold - Put three cards from your graveyard on the bottom of your library: Battlefield Scrounger gets +3/+3 until end of turn. Activate this ability only once each turn, and only if seven or more cards are in your graveyard.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(3, 3, Duration.EndOfTurn),
                new BattlefieldScroungerCost(),
                1,
                new CardsInControllerGraveyardCondition(7));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private BattlefieldScrounger(final BattlefieldScrounger card) {
        super(card);
    }

    @Override
    public BattlefieldScrounger copy() {
        return new BattlefieldScrounger(this);
    }
}

class BattlefieldScroungerCost extends CostImpl {

    BattlefieldScroungerCost() {
        this.addTarget(new TargetCardInYourGraveyard(3, 3, new FilterCard()));
        this.text = "Put three cards from your graveyard on the bottom of your library";
    }


    BattlefieldScroungerCost(final BattlefieldScroungerCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (targets.choose(Outcome.Removal, controllerId, source.getSourceId(), source, game)) {
                for (UUID targetId: targets.get(0).getTargets()) {
                    Card card = game.getCard(targetId);
                    if (card == null || game.getState().getZone(targetId) != Zone.GRAVEYARD) {
                        return false;
                    }
                    paid |= controller.moveCardToLibraryWithInfo(card, source, game, Zone.GRAVEYARD, false, true);
                }
            }

        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, source, game);
    }

    @Override
    public BattlefieldScroungerCost copy() {
        return new BattlefieldScroungerCost(this);
    }
}
