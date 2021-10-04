package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DepartedSoulkeeper extends CardImpl {

    public DepartedSoulkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.transformable = true;
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Departed Soulkeeper can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());

        // If Departed Soulkeeper would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new ExileSourceEffect().setText("exile it instead")));
    }

    private DepartedSoulkeeper(final DepartedSoulkeeper card) {
        super(card);
    }

    @Override
    public DepartedSoulkeeper copy() {
        return new DepartedSoulkeeper(this);
    }
}
