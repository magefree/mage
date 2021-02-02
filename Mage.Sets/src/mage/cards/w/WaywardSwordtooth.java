package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessConditionSourceEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
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
public final class WaywardSwordtooth extends CardImpl {

    public WaywardSwordtooth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Ascend
        this.addAbility(new AscendAbility());

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)));

        // Wayward Sawtooth can't attack or block unless you have the city's blessing.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackBlockUnlessConditionSourceEffect(CitysBlessingCondition.instance))
                .addHint(CitysBlessingHint.instance)
        );
    }

    private WaywardSwordtooth(final WaywardSwordtooth card) {
        super(card);
    }

    @Override
    public WaywardSwordtooth copy() {
        return new WaywardSwordtooth(this);
    }
}
