package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class TriumphantAdventurer extends CardImpl {

    public TriumphantAdventurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // As long as it's your turn, Triumphant Adventurer has first strike.
        this.addAbility(new SimpleStaticAbility(
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                        FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield
                ), MyTurnCondition.instance, "As long as it's your turn, {this} has first strike.")
        ).addHint(MyTurnHint.instance));

        // Whenever Triumphant Adventurer attacks, venture into the dungeon.
        this.addAbility(new AttacksTriggeredAbility(new VentureIntoTheDungeonEffect(), false));
    }

    private TriumphantAdventurer(final TriumphantAdventurer card) {
        super(card);
    }

    @Override
    public TriumphantAdventurer copy() {
        return new TriumphantAdventurer(this);
    }
}
