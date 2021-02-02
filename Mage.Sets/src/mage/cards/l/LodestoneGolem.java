package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class LodestoneGolem extends CardImpl {

    private static final FilterCard filter = new FilterCard("Nonartifact spells");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public LodestoneGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Nonartifact spells cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostIncreasingAllEffect(1, filter, TargetController.ANY)));
    }

    private LodestoneGolem(final LodestoneGolem card) {
        super(card);
    }

    @Override
    public LodestoneGolem copy() {
        return new LodestoneGolem(this);
    }
}
