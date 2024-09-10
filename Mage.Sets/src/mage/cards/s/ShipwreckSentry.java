package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ArtifactEnteredUnderYourControlCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.watchers.common.ArtifactEnteredControllerWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShipwreckSentry extends CardImpl {

    public ShipwreckSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // As long as an artifact entered the battlefield under your control this turn, Shipwreck Sentry can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
                ArtifactEnteredUnderYourControlCondition.instance
        ).setText("as long as an artifact entered the battlefield under your control this turn, " +
                        "{this} can attack as though it didn't have defender"))
                        .addHint(new ConditionHint(ArtifactEnteredUnderYourControlCondition.instance)),
                new ArtifactEnteredControllerWatcher());
    }

    private ShipwreckSentry(final ShipwreckSentry card) {
        super(card);
    }

    @Override
    public ShipwreckSentry copy() {
        return new ShipwreckSentry(this);
    }
}
