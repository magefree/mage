package mage.cards.a;

import mage.MageInt;
import mage.abilities.condition.common.MoreCardsInHandThanOpponentsCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AkutaBornOfAsh extends CardImpl {

    private static final FilterControlledPermanent filterSwamp = new FilterControlledPermanent(SubType.SWAMP, "a Swamp");

    public AkutaBornOfAsh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of your upkeep, if you have more cards in hand than each opponent, you may sacrifice a Swamp. If you do, return Akuta, Born of Ash from your graveyard to the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.GRAVEYARD, TargetController.YOU,
                new DoIfCostPaid(
                        new ReturnSourceFromGraveyardToBattlefieldEffect()
                                .setText("return {this} from your graveyard to the battlefield"),
                        new SacrificeTargetCost(filterSwamp)
                ), false
        ).withInterveningIf(MoreCardsInHandThanOpponentsCondition.instance));
    }

    private AkutaBornOfAsh(final AkutaBornOfAsh card) {
        super(card);
    }

    @Override
    public AkutaBornOfAsh copy() {
        return new AkutaBornOfAsh(this);
    }
}
