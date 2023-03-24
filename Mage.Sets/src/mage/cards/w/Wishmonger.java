package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class Wishmonger extends CardImpl {

    public Wishmonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.UNICORN, SubType.MONGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}: Target creature gains protection from the color of its controller's choice until end of turn. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(new WishmongerEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetCreaturePermanent());
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private Wishmonger(final Wishmonger card) {
        super(card);
    }

    @Override
    public Wishmonger copy() {
        return new Wishmonger(this);
    }
}

class WishmongerEffect extends OneShotEffect {

    public WishmongerEffect() {
        super(Outcome.Protect);
        staticText = "Target creature gains protection from the color of its controller's choice until end of turn";
    }

    public WishmongerEffect(final WishmongerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature == null) {
            return false;
        }
        Player player = game.getPlayer(targetCreature.getControllerId());
        ChoiceColor colorChoice = new ChoiceColor();
        if (player != null && player.canRespond() && player.choose(Outcome.Protect, colorChoice, game)) {
            game.informPlayers(player.getLogName() + " has chosen to give " + targetCreature.getLogName() + " protection from " + colorChoice.getChoice());
            game.addEffect(new GainAbilityTargetEffect(ProtectionAbility.from(colorChoice.getColor())), source);
            return true;
        }
        return false;
    }

    @Override
    public WishmongerEffect copy() {
        return new WishmongerEffect(this);
    }
}
