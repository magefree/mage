package mage.cards.g;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MeldCondition;
import mage.abilities.effects.common.MeldEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class GrafRats extends CardImpl {

    private static final Condition condition = new MeldCondition("Midnight Scavengers");

    public GrafRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.meldsWithClazz = mage.cards.m.MidnightScavengers.class;
        this.meldsToClazz = mage.cards.c.ChitteringHost.class;

        // At the beginning of combat on your turn, if you both own and control Graf Rats and a creature named Midnight Scavengers, exile them, then meld them into Chittering Host.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new MeldEffect(
                "Midnight Scavengers", "Chittering Host"
        )).withInterveningIf(condition));
    }

    private GrafRats(final GrafRats card) {
        super(card);
    }

    @Override
    public GrafRats copy() {
        return new GrafRats(this);
    }
}
