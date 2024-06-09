package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GruulBeastmaster extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public GruulBeastmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.HUMAN, SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Riot
        this.addAbility(new RiotAbility());

        // Whenever Gruul Beastmaster attacks, another target creature you control gets +X/+0 until end of turn, where X is Gruul Beastmaster's power.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(
                xValue, StaticValue.get(0), Duration.EndOfTurn), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private GruulBeastmaster(final GruulBeastmaster card) {
        super(card);
    }

    @Override
    public GruulBeastmaster copy() {
        return new GruulBeastmaster(this);
    }
}
