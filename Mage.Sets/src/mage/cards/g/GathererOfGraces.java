package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.AuraAttachedCount;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

public class GathererOfGraces extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an aura");

    static {
        filter.add(new SubtypePredicate("Aura"));
    }

    public GathererOfGraces(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add("Human");
        this.subtype.add("Druid");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Gatherer of Graces gets +1/+1 for each Aura attached to it.
        AuraAttachedCount count = new AuraAttachedCount(1);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(count, count, Duration.WhileOnBattlefield)));

        // Sacrifice an Aura: Regenerate Gatherer of Graces
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new SacrificeTargetCost(new TargetControlledPermanent(filter))));
    }

    public GathererOfGraces(GathererOfGraces gathererOfGraces) {
        super(gathererOfGraces);
    }

    @Override
    public GathererOfGraces copy() {
        return new GathererOfGraces(this);
    }
}
