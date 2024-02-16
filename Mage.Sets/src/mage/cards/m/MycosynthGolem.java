package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MycosynthGolem extends CardImpl {

    private static final FilterCard filter = new FilterCard("Artifact creature spells you cast");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
    }

    public MycosynthGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{11}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Artifact creature spells you cast have affinity for artifacts.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledSpellsEffect(new AffinityForArtifactsAbility(), filter)));

    }

    private MycosynthGolem(final MycosynthGolem card) {
        super(card);
    }

    @Override
    public MycosynthGolem copy() {
        return new MycosynthGolem(this);
    }
}
