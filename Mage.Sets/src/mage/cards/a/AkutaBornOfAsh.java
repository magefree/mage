
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.MoreCardsInHandThanOpponentsCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class AkutaBornOfAsh extends CardImpl {

    private static final FilterControlledPermanent filterSwamp = new FilterControlledPermanent("a Swamp");
    static {
        filterSwamp.add(SubType.SWAMP.getPredicate());
    }

    public AkutaBornOfAsh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // At the beginning of your upkeep, if you have more cards in hand than each opponent, you may sacrifice a Swamp. If you do, return Akuta, Born of Ash from your graveyard to the battlefield.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.GRAVEYARD,
                    new DoIfCostPaid(new ReturnSourceFromGraveyardToBattlefieldEffect(), new SacrificeTargetCost(new TargetControlledPermanent(filterSwamp))),
                    TargetController.YOU, false),
                MoreCardsInHandThanOpponentsCondition.instance,
                "At the beginning of your upkeep, if you have more cards in hand than each opponent, you may sacrifice a Swamp. If you do, return {this} from your graveyard to the battlefield.");
        this.addAbility(ability);
    }

    private AkutaBornOfAsh(final AkutaBornOfAsh card) {
        super(card);
    }

    @Override
    public AkutaBornOfAsh copy() {
        return new AkutaBornOfAsh(this);
    }
}

