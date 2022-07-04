
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class Genesis extends CardImpl {

    public Genesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.INCARNATION);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, if Genesis is in your graveyard, you may pay {2}{G}. If you do, return target creature card from your graveyard to your hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                Zone.GRAVEYARD, new DoIfCostPaid(new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{2}{G}")), TargetController.YOU, false, false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private Genesis(final Genesis card) {
        super(card);
    }

    @Override
    public Genesis copy() {
        return new Genesis(this);
    }
}
