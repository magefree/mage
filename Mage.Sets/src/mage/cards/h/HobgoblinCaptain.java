package mage.cards.h;

import mage.MageInt;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.PackTacticsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HobgoblinCaptain extends CardImpl {

    public HobgoblinCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Pack tactics — Whenever Hobgoblin Captain attacks, if you attacked with creatures with total power 6 or greater this combat, Hobgoblin Captain gains first strike until end of turn.
        this.addAbility(new PackTacticsAbility(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        )));
    }

    private HobgoblinCaptain(final HobgoblinCaptain card) {
        super(card);
    }

    @Override
    public HobgoblinCaptain copy() {
        return new HobgoblinCaptain(this);
    }
}
