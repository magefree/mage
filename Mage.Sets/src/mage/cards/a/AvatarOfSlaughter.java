
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author fireshoes
 */
public final class AvatarOfSlaughter extends CardImpl {

    public AvatarOfSlaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // All creatures have double strike and attack each turn if able.
        Effect effect = new GainAbilityAllEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("All creatures have double strike");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new AttacksIfAbleAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("and attack each combat if able");
        ability.addEffect(effect);
        this.addAbility(ability, new AttackedThisTurnWatcher());
    }

    private AvatarOfSlaughter(final AvatarOfSlaughter card) {
        super(card);
    }

    @Override
    public AvatarOfSlaughter copy() {
        return new AvatarOfSlaughter(this);
    }
}
