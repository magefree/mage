package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class Infestor extends CardImpl {

    public Infestor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Infestor enters the battlefield, gain control of target creature an opponent controls until end of turn. Untap that creature. It gains haste until end of turn. That creature's owner loses 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new InfestorEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    public Infestor(final Infestor card) {
        super(card);
    }

    @Override
    public Infestor copy() {
        return new Infestor(this);
    }
}

class InfestorEffect extends OneShotEffect {

    public InfestorEffect() {
        super(Outcome.GainControl);
        staticText = "gain control of target creature an opponent controls until end of turn. Untap that creature. It gains haste until end of turn. That creature's owner loses 2 life";
    }

    public InfestorEffect(final InfestorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if(target != null) {
            source.addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
            target.untap(game);
            target.addAbility(HasteAbility.getInstance(), source.getSourceId(), game);
            Player owner = game.getPlayer(target.getOwnerId());
            if(owner != null) {
                owner.loseLife(2, game, false);
            }
            return true;
        }
        return false;
    }

    @Override
    public InfestorEffect copy() {
        return new InfestorEffect(this);
    }
}