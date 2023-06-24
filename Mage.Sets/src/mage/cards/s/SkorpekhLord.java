package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkorpekhLord extends CardImpl {

    public SkorpekhLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.NECRON);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Command Protocols -- Other artifact creatures you control get +1/+0 and have menace.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE, true
        ).setText("and have menace"));
        this.addAbility(ability.withFlavorWord("Command Protocols"));

        // Unearth {2}{B}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{2}{B}")));
    }

    private SkorpekhLord(final SkorpekhLord card) {
        super(card);
    }

    @Override
    public SkorpekhLord copy() {
        return new SkorpekhLord(this);
    }
}
