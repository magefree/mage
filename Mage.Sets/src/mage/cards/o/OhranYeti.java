
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class OhranYeti extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("snow creature");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public OhranYeti(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.YETI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{S}: Target snow creature gains first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(
            FirstStrikeAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{2}{S}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private OhranYeti(final OhranYeti card) {
        super(card);
    }

    @Override
    public OhranYeti copy() {
        return new OhranYeti(this);
    }
}
