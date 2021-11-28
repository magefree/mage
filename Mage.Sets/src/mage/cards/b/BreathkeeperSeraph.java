package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BreathkeeperSeraph extends CardImpl {

    public BreathkeeperSeraph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Breathkeeper Seraph is paired with another creature, each of those creatures has "When this creature dies, you may return it to the battlefield under its owner's control at the beginning of your next upkeep."
        this.addAbility(new SimpleStaticAbility(new GainAbilityPairedEffect(
                new DiesSourceTriggeredAbility(
                        new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(
                                new ReturnSourceFromGraveyardToBattlefieldEffect()
                        )).setText("return it to the battlefield under its owner's control at the beginning of your next upkeep"), true
                ).setTriggerPhrase("When this creature dies, "), "As long as {this} is paired with " +
                "another creature, each of those creatures has \"When this creature dies, you may return " +
                "it to the battlefield under its owner's control at the beginning of your next upkeep.\""
        )));
    }

    private BreathkeeperSeraph(final BreathkeeperSeraph card) {
        super(card);
    }

    @Override
    public BreathkeeperSeraph copy() {
        return new BreathkeeperSeraph(this);
    }
}
