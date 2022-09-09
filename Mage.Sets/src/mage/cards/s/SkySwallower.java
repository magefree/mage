package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class SkySwallower extends CardImpl {

    public SkySwallower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Sky Swallower enters the battlefield, target opponent gains control of all other permanents you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainControlAllPermanentsEffect(Duration.EndOfGame));
        ability.addTarget(new TargetOpponent());
        addAbility(ability);
    }

    private SkySwallower(final SkySwallower card) {
        super(card);
    }

    @Override
    public SkySwallower copy() {
        return new SkySwallower(this);
    }
}

class GainControlAllPermanentsEffect extends ContinuousEffectImpl {
    private static FilterControlledPermanent filter = new FilterControlledPermanent("all other permanents you control");

    public GainControlAllPermanentsEffect(Duration duration) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.Detriment);
        this.staticText = "target opponent gains control of all other permanents you control";
    }

    public GainControlAllPermanentsEffect(final GainControlAllPermanentsEffect effect) {
        super(effect);
    }

    @Override
    public GainControlAllPermanentsEffect copy() {
        return new GainControlAllPermanentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                if (permanent != null && !permanent.getId().equals(source.getSourceId())) {
                    permanent.changeControllerId(targetPlayer.getId(), game, source);
                }
            }
        } else {
            discard();
        }
        return true;
    }
}
