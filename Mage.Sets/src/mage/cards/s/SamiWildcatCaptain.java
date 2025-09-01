package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterNonlandCard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SamiWildcatCaptain extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("spells you cast");

    public SamiWildcatCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Spells you cast have affinity for artifacts.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledSpellsEffect(new AffinityForArtifactsAbility(), filter)
        ));
    }

    private SamiWildcatCaptain(final SamiWildcatCaptain card) {
        super(card);
    }

    @Override
    public SamiWildcatCaptain copy() {
        return new SamiWildcatCaptain(this);
    }
}
