
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class DeathlessBehemoth extends CardImpl {

    private final static FilterControlledPermanent filter = new FilterControlledPermanent("two Eldrazi Scions");

    static {
        filter.add(Predicates.and(
                new SubtypePredicate(SubType.ELDRAZI),
                new SubtypePredicate(SubType.SCION)));
    }

    public DeathlessBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Sacrifice two Eldrazi Scions: Return Deathless Behemoth from your graveyard to your hand. Activate this ability only any time you could cast a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(Zone.GRAVEYARD,
                new ReturnToHandSourceEffect(), new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, true))));
    }

    public DeathlessBehemoth(final DeathlessBehemoth card) {
        super(card);
    }

    @Override
    public DeathlessBehemoth copy() {
        return new DeathlessBehemoth(this);
    }
}
