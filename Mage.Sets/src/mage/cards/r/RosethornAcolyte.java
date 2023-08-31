package mage.cards.r;

import mage.MageInt;
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{2}{G}", "Seasonal Ritual", "{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Seasonal Ritual
        // Add one mana of any color.
        this.getSpellCard().getSpellAbility().addEffect(new AddManaOfAnyColorEffect());

        this.finalizeAdventure();
    }

    private RosethornAcolyte(final RosethornAcolyte card) {
        super(card);
    }

    @Override
    public RosethornAcolyte copy() {
        return new RosethornAcolyte(this);
    }
}
