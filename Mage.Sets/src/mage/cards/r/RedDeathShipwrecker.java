package mage.cards.r;

import java.util.UUID;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 * @author Cguy7777
 */
public final class RedDeathShipwrecker extends CardImpl {

    public RedDeathShipwrecker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CRAB);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Alluring Eyes -- {T}: Goad target creature an opponent controls. That player draws a card. You add {R}.
        Ability ability = new SimpleActivatedAbility(
                new GoadTargetEffect().setText("goad target creature an opponent controls"), new TapSourceCost());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addEffect(new AlluringEyesDrawEffect());
        ability.addEffect(new BasicManaEffect(Mana.RedMana(1))
                .setText("you add {R}. <i>(Until your next turn, that creature attacks each combat if able " +
                        "and attacks a player other than you if able.)</i>"));
        this.addAbility(ability.withFlavorWord("Alluring Eyes"));
    }

    private RedDeathShipwrecker(final RedDeathShipwrecker card) {
        super(card);
    }

    @Override
    public RedDeathShipwrecker copy() {
        return new RedDeathShipwrecker(this);
    }
}

class AlluringEyesDrawEffect extends OneShotEffect {

    AlluringEyesDrawEffect() {
        super(Outcome.Benefit);
        staticText = "that player draws a card";
    }

    private AlluringEyesDrawEffect(final AlluringEyesDrawEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }

        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null) {
            player.drawCards(1, source, game);
        }
        return true;
    }

    @Override
    public AlluringEyesDrawEffect copy() {
        return new AlluringEyesDrawEffect(this);
    }
}
