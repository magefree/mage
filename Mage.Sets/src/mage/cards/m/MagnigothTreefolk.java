
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.abilities.keyword.IslandwalkAbility;
import mage.abilities.keyword.MountainwalkAbility;
import mage.abilities.keyword.PlainswalkAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author emerald000
 */
public final class MagnigothTreefolk extends CardImpl {
    
    private static final FilterLandPermanent filterPlains = new FilterLandPermanent(SubType.PLAINS, "Plains");
    private static final FilterLandPermanent filterIsland = new FilterLandPermanent(SubType.ISLAND, "Island");
    private static final FilterLandPermanent filterSwamp = new FilterLandPermanent(SubType.SWAMP, "Swamp");
    private static final FilterLandPermanent filterMountain = new FilterLandPermanent(SubType.MOUNTAIN, "Mountain");
    private static final FilterLandPermanent filterForest = new FilterLandPermanent(SubType.FOREST, "Forest");

    public MagnigothTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Domain - For each basic land type among lands you control, Magnigoth Treefolk has landwalk of that type.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(new PlainswalkAbility()), 
                        new PermanentsOnTheBattlefieldCondition(filterPlains),
                        "Domain &mdash; For each basic land type among lands you control, {this} has landwalk of that type."));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new IslandwalkAbility(), Duration.WhileOnBattlefield, false, true), 
                new PermanentsOnTheBattlefieldCondition(filterIsland),
                ""));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new SwampwalkAbility(), Duration.WhileOnBattlefield, false, true), 
                new PermanentsOnTheBattlefieldCondition(filterSwamp),
                ""));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new MountainwalkAbility(), Duration.WhileOnBattlefield, false, true), 
                new PermanentsOnTheBattlefieldCondition(filterMountain),
                ""));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new ForestwalkAbility(), Duration.WhileOnBattlefield, false, true), 
                new PermanentsOnTheBattlefieldCondition(filterForest),
                ""));
        this.addAbility(ability);
    }

    private MagnigothTreefolk(final MagnigothTreefolk card) {
        super(card);
    }

    @Override
    public MagnigothTreefolk copy() {
        return new MagnigothTreefolk(this);
    }
}

