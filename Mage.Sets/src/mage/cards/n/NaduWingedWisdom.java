package mage.cards.n;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.RevealTopLandToBattlefieldElseHandEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class NaduWingedWisdom extends CardImpl {

    public NaduWingedWisdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creatures you control have "Whenever this creature becomes the target of a spell or ability, reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand. This ability triggers only twice each turn."
        TriggeredAbility trigger = new BecomesTargetSourceTriggeredAbility(
                new RevealTopLandToBattlefieldElseHandEffect("it")
        );
        trigger.setTriggersLimitEachTurn(2);
        trigger.setTriggerPhrase("Whenever this creature becomes the target of a spell or ability, ");
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledEffect(trigger, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)
        ));
    }

    private NaduWingedWisdom(final NaduWingedWisdom card) {
        super(card);
    }

    @Override
    public NaduWingedWisdom copy() {
        return new NaduWingedWisdom(this);
    }
}