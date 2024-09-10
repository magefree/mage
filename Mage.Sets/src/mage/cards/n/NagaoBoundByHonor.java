
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class NagaoBoundByHonor extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Samurai creatures");

    static {
        filter.add(SubType.SAMURAI.getPredicate());
    }

    public NagaoBoundByHonor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new BushidoAbility(1));
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn, filter, false), false));
    }

    private NagaoBoundByHonor(final NagaoBoundByHonor card) {
        super(card);
    }

    @Override
    public NagaoBoundByHonor copy() {
        return new NagaoBoundByHonor(this);
    }
}
