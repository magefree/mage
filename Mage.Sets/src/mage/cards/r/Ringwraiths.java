package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.TheRingTemptsYouTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Controllable;
import mage.game.Game;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ringwraiths extends CardImpl {

    public Ringwraiths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.WRAITH);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Ringwraiths enters the battlefield, target creature an opponent controls gets -3/-3 until end of turn. If that creature is legendary, its controller loses 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-3, -3));
        ability.addEffect(new RingwraithsEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // When the Ring tempts you, return Ringwraiths from your graveyard to your hand.
        this.addAbility(new TheRingTemptsYouTriggeredAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), false
        ).setTriggerPhrase("When the Ring tempts you, "));
    }

    private Ringwraiths(final Ringwraiths card) {
        super(card);
    }

    @Override
    public Ringwraiths copy() {
        return new Ringwraiths(this);
    }
}

class RingwraithsEffect extends OneShotEffect {

    RingwraithsEffect() {
        super(Outcome.Benefit);
        staticText = "If that creature is legendary, its controller loses 3 life";
    }

    private RingwraithsEffect(final RingwraithsEffect effect) {
        super(effect);
    }

    @Override
    public RingwraithsEffect copy() {
        return new RingwraithsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isLegendary(game))
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(player -> player.loseLife(3, game, source, false) > 0)
                .orElse(false);
    }
}
