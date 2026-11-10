package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class OneBorgEvolved extends CardImpl {

    public OneBorgEvolved(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BORG);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Artifact creatures you control have flying, double strike, vigilance, menace, lifelink, and ward {2}.
        SimpleStaticAbility ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
            FlyingAbility.getInstance(), Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        ).setText("Artifact creatures you control have flying"));
        ability.addEffect(new GainAbilityControlledEffect(
            DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        ).setText(", double strike"));
        ability.addEffect(new GainAbilityControlledEffect(
            VigilanceAbility.getInstance(), Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        ).setText(", vigilance"));
        ability.addEffect(new GainAbilityControlledEffect(
            new MenaceAbility(), Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        ).setText(", menace"));
        ability.addEffect(new GainAbilityControlledEffect(
            LifelinkAbility.getInstance(), Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        ).setText(", lifelink"));
        ability.addEffect(new GainAbilityControlledEffect(
            new WardAbility(new GenericManaCost(2)), Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        ).setText(", and ward {2}"));
        this.addAbility(ability);
    }

    private OneBorgEvolved(final OneBorgEvolved card) {
        super(card);
    }

    @Override
    public OneBorgEvolved copy() {
        return new OneBorgEvolved(this);
    }
}
