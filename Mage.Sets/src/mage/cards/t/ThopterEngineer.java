
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ThopterColorlessToken;

/**
 *
 * @author Wehk
 */
public final class ThopterEngineer extends CardImpl {

    public ThopterEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Thopter Engineer enters the battlefield, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ThopterColorlessToken(), 1)));

        // Artifact creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            HasteAbility.getInstance(), Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));
    }

    private ThopterEngineer(final ThopterEngineer card) {
        super(card);
    }

    @Override
    public ThopterEngineer copy() {
        return new ThopterEngineer(this);
    }
}
