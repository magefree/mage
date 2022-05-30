
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EmbalmAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author stravant
 */
public final class AvenWindGuide extends CardImpl {

    public AvenWindGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Creature tokens you control have flying and vigilance.
        Effect effect = new GainAbilityControlledEffect(
                                FlyingAbility.getInstance(),
                                Duration.WhileOnBattlefield,
                                StaticFilters.FILTER_CREATURE_TOKENS);
        effect.setText("Creature tokens you control have flying");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityControlledEffect(
                        VigilanceAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_CREATURE_TOKENS);
        effect.setText("and vigilance");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Embalm {4}{W}{U}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl<>("{4}{W}{U}"), this));
    }

    private AvenWindGuide(final AvenWindGuide card) {
        super(card);
    }

    @Override
    public AvenWindGuide copy() {
        return new AvenWindGuide(this);
    }
}
