package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author anonymous
 */
public final class GrimReaperLethalLegionnaire extends CardImpl {

    public GrimReaperLethalLegionnaire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Grim Reaper attacks, you may pay {3}{B}. When you do, return target creature card from your
        // graveyard to the battlefield tapped and attacking with a finality counter on it.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(false, true, true, CounterType.FINALITY.createInstance()), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(
                ability, new ManaCostsImpl<>("{3}{B}"), "Pay {3}{B}?"
        )));
    }

    private GrimReaperLethalLegionnaire(final GrimReaperLethalLegionnaire card) {
        super(card);
    }

    @Override
    public GrimReaperLethalLegionnaire copy() {
        return new GrimReaperLethalLegionnaire(this);
    }
}
