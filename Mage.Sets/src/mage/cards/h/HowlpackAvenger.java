package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAnyTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HowlpackAvenger extends CardImpl {

    public HowlpackAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setRed(true);
        this.nightCard = true;

        // Whenever a permanent you control is dealt damage, Howlpack Avenger deals that much damage to any target.
        Ability ability = new DealtDamageAnyTriggeredAbility(new DamageTargetEffect(SavedDamageValue.MUCH),
                StaticFilters.FILTER_CONTROLLED_A_PERMANENT, SetTargetPointer.NONE, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {1}{R}: Howlpack Avenger gets +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                2, 0, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{R}")));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private HowlpackAvenger(final HowlpackAvenger card) {
        super(card);
    }

    @Override
    public HowlpackAvenger copy() {
        return new HowlpackAvenger(this);
    }
}
