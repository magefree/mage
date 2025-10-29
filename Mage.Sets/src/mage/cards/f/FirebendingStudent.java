package mage.cards.f;

import mage.MageInt;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirebendingStudent extends CardImpl {

    public FirebendingStudent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Firebending X, where X is this creature's power.
        this.addAbility(new FirebendingAbility(SourcePermanentPowerValue.NOT_NEGATIVE));
    }

    private FirebendingStudent(final FirebendingStudent card) {
        super(card);
    }

    @Override
    public FirebendingStudent copy() {
        return new FirebendingStudent(this);
    }
}
