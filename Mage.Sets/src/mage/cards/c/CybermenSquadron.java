package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CybermenSquadron extends CardImpl {

    private static final FilterArtifactCreaturePermanent filter =
            new FilterArtifactCreaturePermanent("nonlegendary artifact creatures");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public CybermenSquadron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.CYBERMAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Nonlegendary artifact creatures you control have myriad.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MyriadAbility(), Duration.WhileOnBattlefield, filter
        )));
    }

    private CybermenSquadron(final CybermenSquadron card) {
        super(card);
    }

    @Override
    public CybermenSquadron copy() {
        return new CybermenSquadron(this);
    }
}
