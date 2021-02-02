package mage.cards.s;

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
public final class SnubhornSentry extends CardImpl {

    public SnubhornSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Ascend (If you control ten or more permanents, you get the city's blessing for the rest of the game.)
        this.addAbility(new AscendAbility());

        // Snubhorn Sentry gets +3/+0 as long as you have the city's blessing.
        ContinuousEffect boostSource = new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield);
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(boostSource, CitysBlessingCondition.instance,
                "{this} gets +3/+0 as long as you have the city's blessing");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect).addHint(CitysBlessingHint.instance);
        this.addAbility(ability);
    }

    private SnubhornSentry(final SnubhornSentry card) {
        super(card);
    }

    @Override
    public SnubhornSentry copy() {
        return new SnubhornSentry(this);
    }
}
