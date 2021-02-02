
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Styxo
 */
public final class DawnfeatherEagle extends CardImpl {

    public DawnfeatherEagle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Dawnfeather Eagle enters the battlefield, creatures you control get +1/+1 and gain vigilance until end of turn.
        Effect effect = new BoostControlledEffect(1, 1, Duration.EndOfTurn);
        effect.setText("creatures you control get +1/+1");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        effect = new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("and gain vigilance until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private DawnfeatherEagle(final DawnfeatherEagle card) {
        super(card);
    }

    @Override
    public DawnfeatherEagle copy() {
        return new DawnfeatherEagle(this);
    }
}
