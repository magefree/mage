package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PhenaxGodOfDeception extends CardImpl {

    public PhenaxGodOfDeception(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to blue and black is less than seven, Phenax isn't a creature.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(DevotionCount.UB, 7))
                .addHint(DevotionCount.UB.getHint()));

        // Creatures you control have "{T}: Target player puts the top X cards of their library into their graveyard, where X is this creature's toughness."
        Ability ability = new SimpleActivatedAbility(
                new MillCardsTargetEffect(SourcePermanentToughnessValue.getInstance())
                        .setText("Target player mills X cards, where X is this creature's toughness"), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledEffect(
                        ability, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES, false
                ).setText("Creatures you control have \"{T}: Target player mills X cards, where X is this creature's toughness.\"")
        ));
    }

    private PhenaxGodOfDeception(final PhenaxGodOfDeception card) {
        super(card);
    }

    @Override
    public PhenaxGodOfDeception copy() {
        return new PhenaxGodOfDeception(this);
    }
}
