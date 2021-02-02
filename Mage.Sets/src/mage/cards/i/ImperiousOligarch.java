package mage.cards.i;

import mage.MageInt;
import mage.abilities.keyword.AfterlifeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImperiousOligarch extends CardImpl {

    public ImperiousOligarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Afterlife 1
        this.addAbility(new AfterlifeAbility(1));
    }

    private ImperiousOligarch(final ImperiousOligarch card) {
        super(card);
    }

    @Override
    public ImperiousOligarch copy() {
        return new ImperiousOligarch(this);
    }
}
