package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SarumanTheWhite extends CardImpl {

    public SarumanTheWhite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"),false));

        // Whenever you cast your second spell each turn, amass Orcs 2.
        this.addAbility(new CastSecondSpellTriggeredAbility(new AmassEffect(2, SubType.ORC)));
    }

    private SarumanTheWhite(final SarumanTheWhite card) {
        super(card);
    }

    @Override
    public SarumanTheWhite copy() {
        return new SarumanTheWhite(this);
    }
}
