package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MorningApparition extends CardImpl {

    public MorningApparition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);
        this.transformable = true;
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // If Morning Apparition would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new ExileSourceEffect().setText("exile it instead")));
    }

    private MorningApparition(final MorningApparition card) {
        super(card);
    }

    @Override
    public MorningApparition copy() {
        return new MorningApparition(this);
    }
}
