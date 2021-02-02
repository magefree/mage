
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public final class KrarkClanShaman extends CardImpl {

    private static final FilterControlledPermanent filterSacrificed = new FilterControlledPermanent("an artifact");
    private static final FilterCreaturePermanent filterTargetedCreatures = new FilterCreaturePermanent("creature without flying");

    static {
        filterSacrificed.add(CardType.ARTIFACT.getPredicate());
        filterTargetedCreatures.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }


    public KrarkClanShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageAllEffect(1, filterTargetedCreatures), new SacrificeTargetCost(new TargetControlledPermanent(filterSacrificed))));
    }

    private KrarkClanShaman(final KrarkClanShaman card) {
        super(card);
    }

    @Override
    public KrarkClanShaman copy() {
        return new KrarkClanShaman(this);
    }
}
