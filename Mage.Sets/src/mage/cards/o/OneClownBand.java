package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OneClownBand extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ROBOT, "Robot");

    public OneClownBand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.CLOWN);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}{R}: Target Robot gets +2/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(2, 0), new ManaCostsImpl<>("{2}{R}")
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private OneClownBand(final OneClownBand card) {
        super(card);
    }

    @Override
    public OneClownBand copy() {
        return new OneClownBand(this);
    }
}
