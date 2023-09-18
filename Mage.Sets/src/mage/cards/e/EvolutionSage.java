package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EvolutionSage extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledLandPermanent("a land");

    public EvolutionSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever a land enters the battlefield under your control, proliferate. (Choose any number of permanents and/or players, then give each another counter of each kind already there.)
        this.addAbility(new LandfallAbility(new ProliferateEffect()));
    }

    private EvolutionSage(final EvolutionSage card) {
        super(card);
    }

    @Override
    public EvolutionSage copy() {
        return new EvolutionSage(this);
    }
}
