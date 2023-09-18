package mage.cards.w;

import mage.MageInt;
import mage.abilities.abilityword.KinshipAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WaterspoutWeavers extends CardImpl {

    public WaterspoutWeavers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Kinship - At the beginning of your upkeep, you may look at the top card of your library. If it shares a creature type with Waterspout Weavers, you may reveal it. 
        // If you do, each creature you control gains flying until end of turn.
        this.addAbility(new KinshipAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("each creature you control gains flying until end of turn")));
    }

    private WaterspoutWeavers(final WaterspoutWeavers card) {
        super(card);
    }

    @Override
    public WaterspoutWeavers copy() {
        return new WaterspoutWeavers(this);
    }
}
