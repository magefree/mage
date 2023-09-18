
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author Loki
 */
public final class HellkiteIgniter extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public HellkiteIgniter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying, haste
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        // {1}{R}: Hellkite Igniter gets +X/+0 until end of turn, where X is the number of artifacts you control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(
                        new PermanentsOnBattlefieldCount(filter),
                        StaticValue.get(0),
                        Duration.EndOfTurn,
                        true),
                new ManaCostsImpl<>("{1}{R}")));
    }

    private HellkiteIgniter(final HellkiteIgniter card) {
        super(card);
    }

    @Override
    public HellkiteIgniter copy() {
        return new HellkiteIgniter(this);
    }

}
