
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
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
public final class AnuridScavenger extends CardImpl {

    public AnuridScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));

        // At the beginning of your upkeep, sacrifice Anurid Scavenger unless you put a card from your graveyard on the bottom of your library.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new AnuridScavengerCost()), TargetController.YOU, false));
    }

    private AnuridScavenger(final AnuridScavenger card) {
        super(card);
    }

    @Override
    public AnuridScavenger copy() {
        return new AnuridScavenger(this);
    }
}

class AnuridScavengerCost extends CostImpl {

    AnuridScavengerCost() {
        this.addTarget(new TargetCardInYourGraveyard(1, 1, new FilterCard()));
        this.text = "put a card from your graveyard on the bottom of your library";
    }


    AnuridScavengerCost(final AnuridScavengerCost cost) {
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
    public AnuridScavengerCost copy() {
        return new AnuridScavengerCost(this);
    }
}
