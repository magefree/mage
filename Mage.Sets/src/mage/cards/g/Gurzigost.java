
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000 & L_J
 */
public final class Gurzigost extends CardImpl {

    public Gurzigost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(8);

        // At the beginning of your upkeep, sacrifice Gurzigost unless you put two cards from your graveyard on the bottom of your library.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new GurzigostCost()), TargetController.YOU, false));
        
        // {G}{G}, Discard a card: You may have Gurzigost assign its combat damage this turn as though it weren't blocked.
        Effect effect = new GainAbilitySourceEffect(DamageAsThoughNotBlockedAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("You may have Gurzigost assign its combat damage this turn as though it weren't blocked");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{G}{G}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private Gurzigost(final Gurzigost card) {
        super(card);
    }

    @Override
    public Gurzigost copy() {
        return new Gurzigost(this);
    }
}

class GurzigostCost extends CostImpl {

    GurzigostCost() {
        this.addTarget(new TargetCardInYourGraveyard(2, 2, new FilterCard()));
        this.text = "put two cards from your graveyard on the bottom of your library";
    }


    GurzigostCost(final GurzigostCost cost) {
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
    public GurzigostCost copy() {
        return new GurzigostCost(this);
    }
}
