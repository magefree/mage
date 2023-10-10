
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class GuulDrazOverseer extends CardImpl {

    public GuulDrazOverseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // <i>Landfall</i>- Whenever a land enters the battlefield under your control, other creatures you control get +1/+0 until end of turn. If that land is a Swamp, those creatures get +2/+0 until end of turn instead.
        this.addAbility(new LandfallAbility(Zone.BATTLEFIELD, new GuulDrazOverseerEffect(), false, SetTargetPointer.PERMANENT));
    }

    private GuulDrazOverseer(final GuulDrazOverseer card) {
        super(card);
    }

    @Override
    public GuulDrazOverseer copy() {
        return new GuulDrazOverseer(this);
    }
}

class GuulDrazOverseerEffect extends OneShotEffect {

    public GuulDrazOverseerEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "other creatures you control get +1/+0 until end of turn. If that land is a Swamp, those creatures get +2/+0 until end of turn instead";
    }

    private GuulDrazOverseerEffect(final GuulDrazOverseerEffect effect) {
        super(effect);
    }

    @Override
    public GuulDrazOverseerEffect copy() {
        return new GuulDrazOverseerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent land = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller != null && land != null) {
            int boost = 1;
            if (land.hasSubtype(SubType.SWAMP, game)) {
                boost = 2;
            }
            game.addEffect(new BoostControlledEffect(boost, 0, Duration.EndOfTurn, true), source);
            return true;
        }
        return false;
    }
}
