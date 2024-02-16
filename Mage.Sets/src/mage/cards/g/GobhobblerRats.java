
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class GobhobblerRats extends CardImpl {

    public GobhobblerRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{R}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Hellbent - As long as you have no cards in hand, Gobhobbler Rats gets +1/+0 and has "{B}: Regenerate Gobhobbler Rats."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1,0, Duration.WhileOnBattlefield), HellbentCondition.instance, "<i>Hellbent</i> &mdash; As long as you have no cards in hand, {this} gets +1/+0"));
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}"));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(gainedAbility, Duration.WhileOnBattlefield), HellbentCondition.instance, "and has \"{B}: Regenerate {this}.\""));
        this.addAbility(ability);
    }

    private GobhobblerRats(final GobhobblerRats card) {
        super(card);
    }

    @Override
    public GobhobblerRats copy() {
        return new GobhobblerRats(this);
    }
}
