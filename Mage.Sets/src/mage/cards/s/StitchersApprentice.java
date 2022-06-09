
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.StitchersApprenticeHomunculusToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public final class StitchersApprentice extends CardImpl {

    public StitchersApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HOMUNCULUS);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {1}{U}, {tap}: Create a 2/2 blue Homunculus creature token, then sacrifice a creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new StitchersApprenticeHomunculusToken()), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new StitchersApprenticeEffect());
        this.addAbility(ability);
    }

    private StitchersApprentice(final StitchersApprentice card) {
        super(card);
    }

    @Override
    public StitchersApprentice copy() {
        return new StitchersApprentice(this);
    }
}

class StitchersApprenticeEffect extends OneShotEffect {

    public StitchersApprenticeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "then sacrifice a creature";
    }

    public StitchersApprenticeEffect(final StitchersApprenticeEffect effect) {
        super(effect);
    }

    @Override
    public StitchersApprenticeEffect copy() {
        return new StitchersApprenticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetControlledPermanent(new FilterControlledCreaturePermanent());

            if (target.canChoose(player.getId(), source, game) && player.choose(Outcome.Sacrifice, target, source, game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    return permanent.sacrifice(source, game);
                }
            }
        }
        return false;
    }
}
