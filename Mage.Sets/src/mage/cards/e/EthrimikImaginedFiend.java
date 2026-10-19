package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessConditionSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

public final class EthrimikImaginedFiend extends CardImpl {

    public EthrimikImaginedFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Ethrimik enters, manifest dread.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ManifestDreadEffect()));

        // Other creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, true)));

        // As long as you control another creature, Ethrimik can't attack or block.
        this.addAbility(new SimpleStaticAbility(new CantAttackBlockUnlessConditionSourceEffect(
                new InvertCondition(new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL))
        ).setText("As long as you control another creature, {this} can't attack or block")));
    }

    private EthrimikImaginedFiend(final EthrimikImaginedFiend card) {
        super(card);
    }

    @Override
    public EthrimikImaginedFiend copy() {
        return new EthrimikImaginedFiend(this);
    }
}
