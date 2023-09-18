
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.GoblinToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class SiegeGangCommander extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Goblin");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public SiegeGangCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GoblinToken(), 3), false));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, false)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SiegeGangCommander(final SiegeGangCommander card) {
        super(card);
    }

    @Override
    public SiegeGangCommander copy() {
        return new SiegeGangCommander(this);
    }
}
