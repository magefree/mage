
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author FenrisulfrX
 */
public final class GerrardCapashen extends CardImpl {

    public GerrardCapashen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you gain 1 life for each card in target opponent's hand.
        Ability ability1 = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new GerrardCapashenEffect(),
                TargetController.YOU, false, true);
        ability1.addTarget(new TargetOpponent());
        this.addAbility(ability1);

        // {3}{W}: Tap target creature. Activate this ability only if {this} is attacking.
        Ability ability2 = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(),
                new ManaCostsImpl<>("{3}{W}"), SourceAttackingCondition.instance);
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
    }

    private GerrardCapashen(final GerrardCapashen card) {
        super(card);
    }

    @Override
    public GerrardCapashen copy() {
        return new GerrardCapashen(this);
    }
}

class GerrardCapashenEffect extends OneShotEffect {

    public GerrardCapashenEffect() {
        super(Outcome.GainLife);
        staticText = "you gain 1 life for each card in target opponent's hand.";
    }

    public GerrardCapashenEffect(final GerrardCapashenEffect effect) {
        super(effect);
    }

    @Override
    public GerrardCapashenEffect copy() {
        return new GerrardCapashenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && targetOpponent != null) {
            int cardsInHand = targetOpponent.getHand().size();
            if (cardsInHand > 0) {
                controller.gainLife(cardsInHand, game, source);
            }
            return true;
        }
        return false;
    }
}
