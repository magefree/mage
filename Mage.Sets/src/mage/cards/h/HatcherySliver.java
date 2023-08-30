package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.EachSpellYouCastHasReplicateEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class HatcherySliver extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Sliver spell");

    static {
        filter.add(SubType.SLIVER.getPredicate());
    }

    public HatcherySliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Replicate {1}{G}
        this.addAbility(new ReplicateAbility("{1}{G}"));

        // Each Sliver spell you cast has replicate. The replicate cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(new EachSpellYouCastHasReplicateEffect(filter,
                "A copy of a permanent spell becomes a token.")));

    }

    private HatcherySliver(final HatcherySliver card) {
        super(card);
    }

    @Override
    public HatcherySliver copy() {
        return new HatcherySliver(this);
    }
}
