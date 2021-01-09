package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class IncursionSpecialist extends CardImpl {

    public IncursionSpecialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast your second spell each turn, Incursion Specialist gets +2/+0 until end of turn and can't be blocked this turn.
        Ability ability = new CastSecondSpellTriggeredAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn)
        );
        ability.addEffect(new CantBeBlockedSourceEffect().setText("and can't be blocked this turn"));
        this.addAbility(ability);
    }

    private IncursionSpecialist(final IncursionSpecialist card) {
        super(card);
    }

    @Override
    public IncursionSpecialist copy() {
        return new IncursionSpecialist(this);
    }
}
