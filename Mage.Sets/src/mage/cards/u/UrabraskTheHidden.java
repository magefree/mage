
package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Loki
 */
public final class UrabraskTheHidden extends CardImpl {

    public UrabraskTheHidden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.PRAETOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )));

        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ).setText("creatures your opponents control enter the battlefield tapped")));
    }

    private UrabraskTheHidden(final UrabraskTheHidden card) {
        super(card);
    }

    @Override
    public UrabraskTheHidden copy() {
        return new UrabraskTheHidden(this);
    }
}
