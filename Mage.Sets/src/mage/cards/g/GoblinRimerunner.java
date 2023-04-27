
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class GoblinRimerunner extends CardImpl {

    public GoblinRimerunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Target creature can't block this turn.
        Ability ability = new SimpleActivatedAbility(new CantBlockTargetEffect(Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {snow}: Goblin Rimerunner gains haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{S}")));
    }

    private GoblinRimerunner(final GoblinRimerunner card) {
        super(card);
    }

    @Override
    public GoblinRimerunner copy() {
        return new GoblinRimerunner(this);
    }
}
