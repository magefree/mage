package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.CommanderInPlayCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author TheElk801
 */
public final class LoyalSubordinate extends CardImpl {

    public LoyalSubordinate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Lieutenant — At the beginning of combat on your turn, if you control your commander, each opponent loses 3 life.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new LoseLifeOpponentsEffect(3),
                        TargetController.YOU, false
                ), CommanderInPlayCondition.instance,
                "<i>Lieutenant</i> &mdash; At the beginning of combat "
                + "on your turn, if you control your commander, "
                + "each opponent loses 3 life."
        ));
    }

    private LoyalSubordinate(final LoyalSubordinate card) {
        super(card);
    }

    @Override
    public LoyalSubordinate copy() {
        return new LoyalSubordinate(this);
    }
}
