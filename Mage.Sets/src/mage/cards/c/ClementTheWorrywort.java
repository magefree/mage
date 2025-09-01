package mage.cards.c;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author earchip94
 */
public final class ClementTheWorrywort extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creature you control with lesser mana value");
    private static final FilterPermanent frogFilter = new FilterPermanent(SubType.FROG, "Frogs");

    static {
        filter.add(ClementTheWorrywortPredicate.instance);
    }

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
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new ReturnToHandTargetEffect(), StaticFilters.FILTER_CONTROLLED_CREATURE, false, false
        );
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Frogs you control have "{T}: Add {G} or {U}. Spend this mana only to cast a creature spell."
        Ability gMana = new ConditionalColoredManaAbility(new TapSourceCost(), Mana.GreenMana(1), new ClementTheWorrywortManaBuilder());
        Ability bMana = new ConditionalColoredManaAbility(new TapSourceCost(), Mana.BlueMana(1), new ClementTheWorrywortManaBuilder());
        ability = new SimpleStaticAbility(
                new GainAbilityControlledEffect(gMana, Duration.WhileOnBattlefield, frogFilter, false)
                        .setText("Frogs you control have \"{T}: Add {G} or {U}.")
        );
        ability.addEffect(
                new GainAbilityControlledEffect(bMana, Duration.WhileOnBattlefield, frogFilter, false)
                        .setText("Spend this mana only to cast a creature spell.\"")
        );
        this.addAbility(ability);
    }

    private ClementTheWorrywort(final ClementTheWorrywort card) {
        super(card);
    }

    @Override
    public ClementTheWorrywort copy() {
        return new ClementTheWorrywort(this);
    }
}

enum ClementTheWorrywortPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return CardUtil.getEffectValueFromAbility(
                        input.getSource(), "permanentEnteringBattlefield", Permanent.class
                )
                .filter(permanent -> input.getObject().getManaValue() < permanent.getManaValue())
                .isPresent();
    }
}

class ClementTheWorrywortConditionalMana extends ConditionalMana {

    ClementTheWorrywortConditionalMana(Mana mana) {
        super(mana);
        setComparisonScope(Filter.ComparisonScope.Any);
        addCondition(new CreatureCastManaCondition());
    }
}

class ClementTheWorrywortManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ClementTheWorrywortConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell";
    }
}
