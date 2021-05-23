
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class AnkleShanker extends CardImpl {

    public AnkleShanker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{W}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Whenever Ankle Shanker attacks, creatures you control gain first strike and deathtouch until end of turn.
        Effect effect = new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("creatures you control gain first strike");
        Ability ability = new AttacksTriggeredAbility(effect, false);
        effect = new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("and deathtouch until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private AnkleShanker(final AnkleShanker card) {
        super(card);
    }

    @Override
    public AnkleShanker copy() {
        return new AnkleShanker(this);
    }
}
