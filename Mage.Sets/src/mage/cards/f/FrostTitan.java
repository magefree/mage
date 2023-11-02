package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class FrostTitan extends CardImpl {

    public FrostTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Frost Titan becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays 2.        
        this.addAbility(new BecomesTargetSourceTriggeredAbility(
                new CounterUnlessPaysEffect(new GenericManaCost(2)).setText("counter that spell or ability unless its controller pays {2}"),
                StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS, SetTargetPointer.SPELL, false
        ));

        // Whenever Frost Titan enters the battlefield or attacks, tap target permanent. It doesn't untap during its controller's next untap step.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("It"));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private FrostTitan(final FrostTitan card) {
        super(card);
    }

    @Override
    public FrostTitan copy() {
        return new FrostTitan(this);
    }

}
