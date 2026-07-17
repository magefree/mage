package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class KrangUtromWarlord extends CardImpl {

    public KrangUtromWarlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{9}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.UTROM);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Other artifact creatures you control have flying, trample, indestructible, and haste.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
            FlyingAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
            TrampleAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE, true
        ).setText(", trample"));
        ability.addEffect(new GainAbilityControlledEffect(
            IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE, true
        ).setText(", indestructible"));
        ability.addEffect(new GainAbilityControlledEffect(
            HasteAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE, true
        ).setText(", and haste"));
        this.addAbility(ability);
    }

    private KrangUtromWarlord(final KrangUtromWarlord card) {
        super(card);
    }

    @Override
    public KrangUtromWarlord copy() {
        return new KrangUtromWarlord(this);
    }
}
