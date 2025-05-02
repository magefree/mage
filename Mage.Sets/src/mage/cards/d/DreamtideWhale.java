package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DreamtideWhale extends CardImpl {

    public DreamtideWhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.WHALE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Vanishing 2
        this.addAbility(new VanishingAbility(2));

        // Whenever a player casts their second spell each turn, proliferate.
        this.addAbility(new CastSecondSpellTriggeredAbility(new ProliferateEffect(), TargetController.ANY));
    }

    private DreamtideWhale(final DreamtideWhale card) {
        super(card);
    }

    @Override
    public DreamtideWhale copy() {
        return new DreamtideWhale(this);
    }
}
