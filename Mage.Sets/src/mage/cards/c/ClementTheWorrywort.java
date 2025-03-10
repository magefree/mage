package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author earchip94
 */
public final class ClementTheWorrywort extends CardImpl {

    private static final FilterPermanent frogFilter = new FilterPermanent(SubType.FROG, "Frogs");
    private static final FilterPermanent creatureFilter = new FilterControlledCreaturePermanent();

    public ClementTheWorrywort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Clement, the Worrywort or another creature you control enters, return up to one target creature you control with lesser mana value to its owner's hand.
        Ability bounceAbility = new EntersBattlefieldControlledTriggeredAbility(
            new ReturnToHandTargetEffect(), creatureFilter);
        bounceAbility.addTarget(new TargetPermanent(0, 1, creatureFilter));
        this.addAbility(bounceAbility);


        // Frogs you control have "{T}: Add {G} or {U}. Spend this mana only to cast a creature spell."
        Ability gMana = new ConditionalColoredManaAbility(new TapSourceCost(), Mana.GreenMana(1), new ClementWorrywortManaBuilder());
        Ability bMana = new ConditionalColoredManaAbility(new TapSourceCost(), Mana.BlueMana(1), new ClementWorrywortManaBuilder());
        this.addAbility(new SimpleStaticAbility(
            new GainAbilityControlledEffect(gMana, Duration.WhileOnBattlefield, frogFilter, false)
        ));
        this.addAbility(new SimpleStaticAbility(
            new GainAbilityControlledEffect(bMana, Duration.WhileOnBattlefield, frogFilter, false)
        ));
    }

    private ClementTheWorrywort(final ClementTheWorrywort card) {
        super(card);
    }

    @Override
    public ClementTheWorrywort copy() {
        return new ClementTheWorrywort(this);
    }
}

class ClementWorrywortConditionalMana extends ConditionalMana {

    ClementWorrywortConditionalMana(Mana mana) {
        super(mana);
        setComparisonScope(Filter.ComparisonScope.Any);
        addCondition(new CreatureCastManaCondition());
    }
}

class ClementWorrywortManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ClementWorrywortConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell";
    }
}