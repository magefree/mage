package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class AshrootAnimist extends CardImpl {

    public AshrootAnimist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When this creature attacks, another target creature you control gains trample and gets +X/+X until end of turn, where X is this creature's power.
        Ability ability = new AttacksTriggeredAbility(
                new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn).setText("another target creature you control gains trample"),
                false
        );
        ability.addEffect(new BoostTargetEffect(SourcePermanentPowerValue.NOT_NEGATIVE, SourcePermanentPowerValue.NOT_NEGATIVE, Duration.EndOfTurn)
                .setText("and gets +X/+X until end of turn, where X is this creature's power"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private AshrootAnimist(final AshrootAnimist card) {
        super(card);
    }

    @Override
    public AshrootAnimist copy() {
        return new AshrootAnimist(this);
    }
}
