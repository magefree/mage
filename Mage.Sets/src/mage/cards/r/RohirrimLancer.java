package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RohirrimLancer extends CardImpl {

    public RohirrimLancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Rohirrim Lancer dies, the Ring tempts you.
        this.addAbility(new DiesSourceTriggeredAbility(new TheRingTemptsYouEffect()));
    }

    private RohirrimLancer(final RohirrimLancer card) {
        super(card);
    }

    @Override
    public RohirrimLancer copy() {
        return new RohirrimLancer(this);
    }
}
