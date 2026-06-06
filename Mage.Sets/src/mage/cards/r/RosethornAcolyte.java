package mage.cards.r;

import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RosethornAcolyte extends AdventureCard {

    public RosethornAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELF, SubType.DRUID}, "{2}{G}",
                "Seasonal Ritual",
                new CardType[]{CardType.SORCERY}, "{G}");

        // Rosethorn Acolyte
        this.getLeftHalfCard().setPT(2, 3);

        // {T}: Add one mana of any color.
        this.getLeftHalfCard().addAbility(new AnyColorManaAbility());

        // Seasonal Ritual
        // Add one mana of any color.
        this.getRightHalfCard().getSpellAbility().addEffect(new AddManaOfAnyColorEffect());

        finalizeCard();
    }

    private RosethornAcolyte(final RosethornAcolyte card) {
        super(card);
    }

    @Override
    public RosethornAcolyte copy() {
        return new RosethornAcolyte(this);
    }
}
