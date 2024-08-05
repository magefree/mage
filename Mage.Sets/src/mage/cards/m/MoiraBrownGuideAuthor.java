package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.WastelandSurvivalGuideToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MoiraBrownGuideAuthor extends CardImpl {

    public MoiraBrownGuideAuthor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Moira Brown, Guide Author enters the battlefield, create a colorless Equipment artifact token named Wasteland Survival Guide with "Equipped creature gets +1/+1 for each quest counter among permanents you control" and equip {1}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WastelandSurvivalGuideToken())));

        // Whenever you attack, put a quest counter on target nonland permanent you control.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new AddCountersTargetEffect(CounterType.QUEST.createInstance()), 1
        );
        ability.addTarget(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND));
        this.addAbility(ability);
    }

    private MoiraBrownGuideAuthor(final MoiraBrownGuideAuthor card) {
        super(card);
    }

    @Override
    public MoiraBrownGuideAuthor copy() {
        return new MoiraBrownGuideAuthor(this);
    }
}
