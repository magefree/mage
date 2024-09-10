
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Loki
 */
public final class SheoldredWhisperingOne extends CardImpl {

    public SheoldredWhisperingOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.PRAETOR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());

        // At the beginning of your upkeep, return target creature card from your graveyard to the battlefield.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(false), TargetController.YOU, false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // At the beginning of each opponent's upkeep, that player sacrifices a creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "that player"), TargetController.OPPONENT, false));
    }

    private SheoldredWhisperingOne(final SheoldredWhisperingOne card) {
        super(card);
    }

    @Override
    public SheoldredWhisperingOne copy() {
        return new SheoldredWhisperingOne(this);
    }

}
