package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class SkirsdagFlayer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.HUMAN, "Human");

    public SkirsdagFlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}{B}, {tap}, Sacrifice a Human: Destroy target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{3}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SkirsdagFlayer(final SkirsdagFlayer card) {
        super(card);
    }

    @Override
    public SkirsdagFlayer copy() {
        return new SkirsdagFlayer(this);
    }
}
