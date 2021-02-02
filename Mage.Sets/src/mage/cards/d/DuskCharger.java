package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DuskCharger extends CardImpl {

    public DuskCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Ascend
        this.addAbility(new AscendAbility());

        // Dusk Charger gets +2/+2 as long as you have the city's blessing.
        ContinuousEffect boostSource = new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield);
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(boostSource, CitysBlessingCondition.instance,
                "{this} gets +2/+2 as long as you have the city's blessing");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect).addHint(CitysBlessingHint.instance);
        this.addAbility(ability);
    }

    private DuskCharger(final DuskCharger card) {
        super(card);
    }

    @Override
    public DuskCharger copy() {
        return new DuskCharger(this);
    }
}
