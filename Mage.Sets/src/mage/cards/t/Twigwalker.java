
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class Twigwalker extends CardImpl {

    public Twigwalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{G}, Sacrifice Twigwalker: Two target creatures each get +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent(2, 2, new FilterCreaturePermanent("creatures each"), false));
        this.addAbility(ability);
    }

    private Twigwalker(final Twigwalker card) {
        super(card);
    }

    @Override
    public Twigwalker copy() {
        return new Twigwalker(this);
    }
}
