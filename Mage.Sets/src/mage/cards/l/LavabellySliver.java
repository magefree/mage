package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LavabellySliver extends CardImpl {

    public LavabellySliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sliver creatures you control have "When this creature enters the battlefield, it deals 1 damage to target player or planeswalker and you gain 1 life."
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(1, "it"),
                false, true
        ).setTriggerPhrase("When this creature enters the battlefield, ");
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                ability, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_SLIVERS
        ).withForceQuotes()));
    }

    private LavabellySliver(final LavabellySliver card) {
        super(card);
    }

    @Override
    public LavabellySliver copy() {
        return new LavabellySliver(this);
    }
}
