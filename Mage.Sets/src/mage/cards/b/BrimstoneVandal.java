package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BecomeDayAsEntersAbility;
import mage.abilities.common.BecomesDayOrNightTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrimstoneVandal extends CardImpl {

    public BrimstoneVandal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // If it's neither day nor night, it becomes day as Brimstone Vandal enters the battlefield.
        this.addAbility(new BecomeDayAsEntersAbility());

        // Whenever day becomes night or night becomes day, Brimstone Vandal deals 1 damage to each opponent.
        this.addAbility(new BecomesDayOrNightTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT)
        ));
    }

    private BrimstoneVandal(final BrimstoneVandal card) {
        super(card);
    }

    @Override
    public BrimstoneVandal copy() {
        return new BrimstoneVandal(this);
    }
}
