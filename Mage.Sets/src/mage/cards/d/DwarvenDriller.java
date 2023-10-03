package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class DwarvenDriller extends CardImpl {

    public DwarvenDriller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Destroy target land unless its controller has Dwarven Driller deal 2 damage to them.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DwarvenDrillerEffect(), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private DwarvenDriller(final DwarvenDriller card) {
        super(card);
    }

    @Override
    public DwarvenDriller copy() {
        return new DwarvenDriller(this);
    }
}

class DwarvenDrillerEffect extends OneShotEffect {

    public DwarvenDrillerEffect() {
        super(Outcome.Detriment);
        this.staticText = "Destroy target land unless its controller has {this} deal 2 damage to them";
    }

    private DwarvenDrillerEffect(final DwarvenDrillerEffect effect) {
        super(effect);
    }

    @Override
    public DwarvenDrillerEffect copy() {
        return new DwarvenDrillerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                String message = "Have Dwarven Driller do 2 damage to you?";
                if (player.chooseUse(Outcome.Damage, message, source, game)) {
                    player.damage(2, source.getSourceId(), source, game);
                } else {
                    permanent.destroy(source, game, false);
                }
                return true;
            }
        }
        return false;
    }
}
