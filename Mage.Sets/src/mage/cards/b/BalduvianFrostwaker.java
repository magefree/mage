
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801 & L_J
 */
public final class BalduvianFrostwaker extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("snow land");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public BalduvianFrostwaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {U}, {T}: Target snow land becomes a 2/2 blue Elemental creature with flying. It's still a land.
        Ability ability = new SimpleActivatedAbility(
            new BecomesCreatureTargetEffect(
                new CreatureToken(2, 2, "2/2 blue Elemental creature with flying", SubType.ELEMENTAL)
                    .withColor("U").withAbility(FlyingAbility.getInstance()),
                false,
                true,
                Duration.Custom
            ),
            new ManaCostsImpl<>("{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BalduvianFrostwaker(final BalduvianFrostwaker card) {
        super(card);
    }

    @Override
    public BalduvianFrostwaker copy() {
        return new BalduvianFrostwaker(this);
    }
}
