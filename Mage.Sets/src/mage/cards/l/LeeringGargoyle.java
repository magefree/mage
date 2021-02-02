
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author TheElk801
 */
public final class LeeringGargoyle extends CardImpl {

    public LeeringGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {tap}: Leering Gargoyle gets -2/+2 and loses flying until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(-2, 2, Duration.EndOfTurn)
                        .setText("{this} gets -2/+2"),
                new TapSourceCost()
        );
        ability.addEffect(
                new LoseAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn)
                        .setText("and loses flying until end of turn")
        );
        this.addAbility(ability);
    }

    private LeeringGargoyle(final LeeringGargoyle card) {
        super(card);
    }

    @Override
    public LeeringGargoyle copy() {
        return new LeeringGargoyle(this);
    }
}
