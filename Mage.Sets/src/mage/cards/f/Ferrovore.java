

package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class Ferrovore extends CardImpl {
     private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public Ferrovore (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(3, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    public Ferrovore (final Ferrovore card) {
        super(card);
    }

    @Override
    public Ferrovore copy() {
        return new Ferrovore(this);
    }

}
