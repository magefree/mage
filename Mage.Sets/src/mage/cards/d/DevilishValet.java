package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllianceAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DevilishValet extends CardImpl {

    public DevilishValet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DEVIL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Alliance — Whenever another creature enters the battlefield under your control, double Devilish Valet's power until end of turn.
        this.addAbility(new AllianceAbility(new DevilishValetEffect()));
    }

    private DevilishValet(final DevilishValet card) {
        super(card);
    }

    @Override
    public DevilishValet copy() {
        return new DevilishValet(this);
    }
}

class DevilishValetEffect extends OneShotEffect {

    DevilishValetEffect() {
        super(Outcome.Benefit);
        staticText = "double {this}'s power until end of turn";
    }

    private DevilishValetEffect(final DevilishValetEffect effect) {
        super(effect);
    }

    @Override
    public DevilishValetEffect copy() {
        return new DevilishValetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        game.addEffect(new BoostSourceEffect(
                permanent.getPower().getValue(), 0, Duration.EndOfTurn
        ), source);
        return true;
    }
}
