package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
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
public final class PhalanxVanguard extends CardImpl {

    public PhalanxVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever an artifact enters the battlefield under your control, Phalanx Vanguard gets +1/+0 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN
        ));
    }

    private PhalanxVanguard(final PhalanxVanguard card) {
        super(card);
    }

    @Override
    public PhalanxVanguard copy() {
        return new PhalanxVanguard(this);
    }
}
