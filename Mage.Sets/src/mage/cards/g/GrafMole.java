package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class GrafMole extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.CLUE, "a Clue");

    public GrafMole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.MOLE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you sacrifice a Clue, you gain 3 life.
        this.addAbility(new SacrificePermanentTriggeredAbility(new GainLifeEffect(3), filter));
    }

    private GrafMole(final GrafMole card) {
        super(card);
    }

    @Override
    public GrafMole copy() {
        return new GrafMole(this);
    }
}
