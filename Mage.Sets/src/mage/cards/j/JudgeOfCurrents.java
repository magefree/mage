package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class JudgeOfCurrents extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.MERFOLK, "a Merfolk you control");

    public JudgeOfCurrents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a Merfolk you control becomes tapped, you may gain 1 life.
        this.addAbility(new BecomesTappedTriggeredAbility(new GainLifeEffect(1), true, filter));
    }

    private JudgeOfCurrents(final JudgeOfCurrents card) {
        super(card);
    }

    @Override
    public JudgeOfCurrents copy() {
        return new JudgeOfCurrents(this);
    }
}
