package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
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
public final class VoyagerQuickwelder extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("artifact spells");

    public VoyagerQuickwelder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Artifact spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private VoyagerQuickwelder(final VoyagerQuickwelder card) {
        super(card);
    }

    @Override
    public VoyagerQuickwelder copy() {
        return new VoyagerQuickwelder(this);
    }
}
