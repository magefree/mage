package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeekerOfSunlight extends CardImpl {

    public SeekerOfSunlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{G}: Seeker of Sunlight explores. Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                new ExploreSourceEffect(true, "{this}"), new ManaCostsImpl<>("{2}{G}")
        ));
    }

    private SeekerOfSunlight(final SeekerOfSunlight card) {
        super(card);
    }

    @Override
    public SeekerOfSunlight copy() {
        return new SeekerOfSunlight(this);
    }
}
