package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaptiveWeird extends CardImpl {

    public CaptiveWeird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.WEIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.c.CompleatedConjurer.class;

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {3}{R/P}: Transform Captive Weird. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{3}{R/P}")));
    }

    private CaptiveWeird(final CaptiveWeird card) {
        super(card);
    }

    @Override
    public CaptiveWeird copy() {
        return new CaptiveWeird(this);
    }
}
