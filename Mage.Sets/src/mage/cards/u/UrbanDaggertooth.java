package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrbanDaggertooth extends CardImpl {

    public UrbanDaggertooth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Enrage — Whenever Urban Daggertooth is dealt damage, proliferate.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new ProliferateEffect(), false, true));
    }

    private UrbanDaggertooth(final UrbanDaggertooth card) {
        super(card);
    }

    @Override
    public UrbanDaggertooth copy() {
        return new UrbanDaggertooth(this);
    }
}
