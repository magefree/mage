package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OtterPenguin extends CardImpl {

    public OtterPenguin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you draw your second card each turn, this creature gets +1/+2 until end of turn and can't be blocked this turn.
        Ability ability = new DrawNthCardTriggeredAbility(new BoostSourceEffect(1, 2, Duration.EndOfTurn));
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn).setText("and can't be blocked this turn"));
        this.addAbility(ability);
    }

    private OtterPenguin(final OtterPenguin card) {
        super(card);
    }

    @Override
    public OtterPenguin copy() {
        return new OtterPenguin(this);
    }
}
