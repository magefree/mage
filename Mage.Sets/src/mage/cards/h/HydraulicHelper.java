package mage.cards.h;

import java.util.UUID;

import mage.MageInt;
import mage.Mana;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PowerstoneToken;

/**
 * @author muz
 */
public final class HydraulicHelper extends CardImpl {

    public HydraulicHelper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {T}: Add {U}. This mana can't be spent to cast a nonartifact spell.
        this.addAbility(new ConditionalColoredManaAbility(Mana.BlueMana(1), PowerstoneToken.makeBuilder()));
    }

    private HydraulicHelper(final HydraulicHelper card) {
        super(card);
    }

    @Override
    public HydraulicHelper copy() {
        return new HydraulicHelper(this);
    }
}
