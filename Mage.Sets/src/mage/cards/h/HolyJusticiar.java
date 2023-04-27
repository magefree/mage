package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
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
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class HolyJusticiar extends CardImpl {

    public HolyJusticiar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}{W}, {tap}: Tap target creature. If that creature is a Zombie, exile it.
        Ability ability = new SimpleActivatedAbility(new HolyJusticiarEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HolyJusticiar(final HolyJusticiar card) {
        super(card);
    }

    @Override
    public HolyJusticiar copy() {
        return new HolyJusticiar(this);
    }
}

class HolyJusticiarEffect extends OneShotEffect {

    HolyJusticiarEffect() {
        super(Outcome.Detriment);
        staticText = "Tap target creature. If that creature is a Zombie, exile it";
    }

    private HolyJusticiarEffect(final HolyJusticiarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (player == null || creature == null) {
            return false;
        }
        creature.tap(source, game);
        if (creature.hasSubtype(SubType.ZOMBIE, game)) {
            player.moveCards(creature, Zone.EXILED, source, game);
        }
        return true;
    }

    @Override
    public HolyJusticiarEffect copy() {
        return new HolyJusticiarEffect(this);
    }
}
