package mage.cards.e;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

public class EyeOfMalcator extends CardImpl {

    public EyeOfMalcator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        //When Eye of Malcator enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        //Whenever another artifact enters the battlefield under your control, Eye of Malcator becomes a 4/4 Phyrexian
        //Eye artifact creature until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new BecomesCreatureSourceEffect(
                        new CreatureToken(
                                4, 4, "4/4 Phyrexian Eye artifact creature", SubType.PHYREXIAN, SubType.EYE
                        ).withType(CardType.ARTIFACT), CardType.ARTIFACT, Duration.EndOfTurn
                ).setText("{this} becomes a 4/4 Phyrexian Eye artifact creature until end of turn"),
                StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT
        ).setTriggerPhrase("Whenever another artifact enters the battlefield under your control, "));
    }

    private EyeOfMalcator(final EyeOfMalcator card) {
        super(card);
    }

    @Override
    public EyeOfMalcator copy() {
        return new EyeOfMalcator(this);
    }
}
