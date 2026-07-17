package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DundoolinWeaver extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledCreaturePermanent("you control three or more creatures"),
            ComparisonType.MORE_THAN, 2
    );

    public DundoolinWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature enters, if you control three or more creatures, return target permanent card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect()).withInterveningIf(condition);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_PERMANENT));
        this.addAbility(ability.addHint(CreaturesYouControlHint.instance));
    }

    private DundoolinWeaver(final DundoolinWeaver card) {
        super(card);
    }

    @Override
    public DundoolinWeaver copy() {
        return new DundoolinWeaver(this);
    }
}
