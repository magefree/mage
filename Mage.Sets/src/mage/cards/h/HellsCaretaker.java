package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HellsCaretaker extends CardImpl {

    public HellsCaretaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}, Sacrifice a creature: Return target creature card from your graveyard to the battlefield. Activate this ability only during your upkeep.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                new TapSourceCost(), IsStepCondition.getMyUpkeep()
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private HellsCaretaker(final HellsCaretaker card) {
        super(card);
    }

    @Override
    public HellsCaretaker copy() {
        return new HellsCaretaker(this);
    }
}
