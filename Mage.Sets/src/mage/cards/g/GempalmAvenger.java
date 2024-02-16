
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class GempalmAvenger extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Soldier creatures");

    static {
        filter.add(SubType.SOLDIER.getPredicate());
    }

    public GempalmAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Cycling {2}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{W}")));

        // When you cycle Gempalm Avenger, Soldier creatures get +1/+1 and gain first strike until end of turn.
        Ability ability = new CycleTriggeredAbility(
                new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, false).setText("Soldier creatures get +1/+1")
        );
        Effect effect = new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filter);
        effect.setText("and gain first strike until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private GempalmAvenger(final GempalmAvenger card) {
        super(card);
    }

    @Override
    public GempalmAvenger copy() {
        return new GempalmAvenger(this);
    }
}
