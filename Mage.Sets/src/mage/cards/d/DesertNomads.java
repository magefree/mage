package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceByPermanentsEffect;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class DesertNomads extends CardImpl {

    private static final FilterControlledLandPermanent filterWalk = new FilterControlledLandPermanent(SubType.DESERT, "desert");
    private static final FilterLandPermanent filterPrevent = new FilterLandPermanent(SubType.DESERT, "Deserts");

    public DesertNomads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN, SubType.NOMAD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Desertwalk
        this.addAbility(new LandwalkAbility(filterWalk));

        // Prevent all damage that would be dealt to Desert Nomads by Deserts.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToSourceByPermanentsEffect(filterPrevent)));
    }

    private DesertNomads(final DesertNomads card) {
        super(card);
    }

    @Override
    public DesertNomads copy() {
        return new DesertNomads(this);
    }
}
