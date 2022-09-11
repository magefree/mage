package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForbiddingSpirit extends CardImpl {

    public ForbiddingSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Forbidding Spirit enters the battlefield, until your next turn, creatures can't attack you or a planeswalker you control unless their controller pays {2} for each of those creatures.
        ContinuousEffect effect = new CantAttackYouUnlessPayAllEffect(
                new ManaCostsImpl<>("{2}"), true
        );
        effect.setDuration(Duration.UntilYourNextTurn);
        effect.setText("until your next turn, creatures can't attack you or planeswalkers you control " +
                "unless their controller pays {2} for each of those creatures.");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));
    }

    private ForbiddingSpirit(final ForbiddingSpirit card) {
        super(card);
    }

    @Override
    public ForbiddingSpirit copy() {
        return new ForbiddingSpirit(this);
    }
}
