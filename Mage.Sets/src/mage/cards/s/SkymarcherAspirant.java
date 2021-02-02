package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.keyword.FlyingAbility;
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
public final class SkymarcherAspirant extends CardImpl {

    public SkymarcherAspirant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Ascend
        this.addAbility(new AscendAbility());

        // Skymarcher Aspirant has flying as long as you have the city's blessing.
        ContinuousEffect boostSource = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield);
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(boostSource, CitysBlessingCondition.instance,
                "{this} has flying as long as you have the city's blessing");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect).addHint(CitysBlessingHint.instance);
        this.addAbility(ability);
    }

    private SkymarcherAspirant(final SkymarcherAspirant card) {
        super(card);
    }

    @Override
    public SkymarcherAspirant copy() {
        return new SkymarcherAspirant(this);
    }
}
