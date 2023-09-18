package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnthusiasticMechanaut extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("artifact spells");

    public EnthusiasticMechanaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{U}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Artifact spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private EnthusiasticMechanaut(final EnthusiasticMechanaut card) {
        super(card);
    }

    @Override
    public EnthusiasticMechanaut copy() {
        return new EnthusiasticMechanaut(this);
    }
}
