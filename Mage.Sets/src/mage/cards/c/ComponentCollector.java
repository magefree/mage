package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomeDayAsEntersAbility;
import mage.abilities.common.BecomesDayOrNightTriggeredAbility;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ComponentCollector extends CardImpl {

    public ComponentCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // If it's neither day nor night, it becomes day as Component Collector enters the battlefield.
        this.addAbility(new BecomeDayAsEntersAbility());

        // Whenever day becomes night or night becomes day, you may tap or untap target nonland permanent.
        Ability ability = new BecomesDayOrNightTriggeredAbility(new MayTapOrUntapTargetEffect());
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private ComponentCollector(final ComponentCollector card) {
        super(card);
    }

    @Override
    public ComponentCollector copy() {
        return new ComponentCollector(this);
    }
}
