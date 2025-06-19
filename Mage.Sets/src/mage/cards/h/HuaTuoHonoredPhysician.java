package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HuaTuoHonoredPhysician extends CardImpl {

    public HuaTuoHonoredPhysician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}: Put target creature card from your graveyard on top of your library. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new PutOnLibraryTargetEffect(true), new TapSourceCost(),
                MyTurnBeforeAttackersDeclaredCondition.instance
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private HuaTuoHonoredPhysician(final HuaTuoHonoredPhysician card) {
        super(card);
    }

    @Override
    public HuaTuoHonoredPhysician copy() {
        return new HuaTuoHonoredPhysician(this);
    }
}
