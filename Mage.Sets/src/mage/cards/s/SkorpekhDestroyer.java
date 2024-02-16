package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkorpekhDestroyer extends CardImpl {

    public SkorpekhDestroyer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Hyperphase Threshers -- Whenever an artifact enters the battlefield under your control, Skorpekh Destroyer gains first strike until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn)
                , StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN
        ).withFlavorWord("Hyperphase Threshers"));
    }

    private SkorpekhDestroyer(final SkorpekhDestroyer card) {
        super(card);
    }

    @Override
    public SkorpekhDestroyer copy() {
        return new SkorpekhDestroyer(this);
    }
}
