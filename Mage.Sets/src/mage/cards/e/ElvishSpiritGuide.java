
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class ElvishSpiritGuide extends CardImpl {

    public ElvishSpiritGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Exile Elvish Spirit Guide from your hand: Add {G}.
        this.addAbility(new SimpleManaAbility(Zone.HAND, Mana.GreenMana(1), new ExileSourceFromHandCost()));
    }

    private ElvishSpiritGuide(final ElvishSpiritGuide card) {
        super(card);
    }

    @Override
    public ElvishSpiritGuide copy() {
        return new ElvishSpiritGuide(this);
    }
}


class ExileSourceFromHandCost extends CostImpl {

    public ExileSourceFromHandCost() {
        this.text = "Exile {this} from your hand";
    }

    public ExileSourceFromHandCost(ExileSourceFromHandCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Card card = game.getCard(source.getSourceId());
        Player player = game.getPlayer(controllerId);
        if (player != null && player.getHand().contains(source.getSourceId()) && card != null) {
            paid = card.moveToExile(ability.getSourceId(), "from Hand", source, game);
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        if (player != null && player.getHand().contains(source.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public ExileSourceFromHandCost copy() {
        return new ExileSourceFromHandCost(this);
    }

}
