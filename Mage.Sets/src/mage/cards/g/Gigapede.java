package mage.cards.g;

import mage.MageInt;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.SourceInGraveyardCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class Gigapede extends CardImpl {

    public Gigapede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(1);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());

        // At the beginning of your upkeep, if Gigapede is in your graveyard, you may discard a card. If you do, return Gigapede to your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.GRAVEYARD,
                TargetController.YOU, new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect().setText("return this card to your hand"), new DiscardCardCost()),
                false).withInterveningIf(SourceInGraveyardCondition.instance));
    }

    private Gigapede(final Gigapede card) {
        super(card);
    }

    @Override
    public Gigapede copy() {
        return new Gigapede(this);
    }
}
