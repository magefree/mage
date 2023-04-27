package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeartfireImmolator extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public HeartfireImmolator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());

        // {R}, Sacrifice Heartfire Immolator: It deals damage equal to its power to target creature or planeswalker.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(xValue)
                        .setText("it deals damage equal to its power to target creature or planeswalker"),
                new ManaCostsImpl<>("{R}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);
    }

    private HeartfireImmolator(final HeartfireImmolator card) {
        super(card);
    }

    @Override
    public HeartfireImmolator copy() {
        return new HeartfireImmolator(this);
    }
}
