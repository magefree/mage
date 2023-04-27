package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ForceOfNature extends CardImpl {

    public ForceOfNature(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, Force of Nature deals 8 damage to you unless you pay {G}{G}{G}{G}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ForceOfNatureEffect(), TargetController.YOU, false));

    }

    private ForceOfNature(final ForceOfNature card) {
        super(card);
    }

    @Override
    public ForceOfNature copy() {
        return new ForceOfNature(this);
    }
}

class ForceOfNatureEffect extends OneShotEffect {

    public ForceOfNatureEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 8 damage to you unless you pay {G}{G}{G}{G}";
    }

    public ForceOfNatureEffect(final ForceOfNatureEffect effect) {
        super(effect);
    }

    @Override
    public ForceOfNatureEffect copy() {
        return new ForceOfNatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cost cost = new ManaCostsImpl<>("{G}{G}{G}{G}");
            if (!(controller.chooseUse(Outcome.Benefit, "Pay {G}{G}{G}{G}?", source, game)
                    && cost.pay(source, game, source, controller.getId(), false, null))) {
                controller.damage(8, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }

}
