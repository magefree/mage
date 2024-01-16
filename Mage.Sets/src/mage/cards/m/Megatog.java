
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public final class Megatog extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public Megatog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.ATOG);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Sacrifice an artifact: Megatog gets +3/+3 and gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(3, 3, Duration.EndOfTurn)
                .setText("{this} gets +3/+3"), new SacrificeTargetCost(filter));
        ability.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains trample until end of turn"));
        this.addAbility(ability);
    }

    private Megatog(final Megatog card) {
        super(card);
    }

    @Override
    public Megatog copy() {
        return new Megatog(this);
    }
}
