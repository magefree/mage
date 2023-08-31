
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.ParleyCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class WoodvineElemental extends CardImpl {
    
    static final private String rule = "<i>Parley</i> &mdash; Whenever {this} attacks, each player reveals the top card of their library. "
                        + "For each nonland card revealed this way, attacking creatures you control get +1/+1 until end of turn. Then each player draws a card.";
    
    public WoodvineElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{W}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Parley - Whenever Woodvine Elemental attacks, each player reveals the top card of their library.
        // For each nonland card revealed this way, attacking creatures you control get +1/+1 until end of turn. Then each player draws a card.
        Ability ability = new AttacksTriggeredAbility(new WoodvineElementalEffect(), false, rule);
        Effect effect = new DrawCardAllEffect(1);
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private WoodvineElemental(final WoodvineElemental card) {
        super(card);
    }

    @Override
    public WoodvineElemental copy() {
        return new WoodvineElemental(this);
    }
}

class WoodvineElementalEffect extends OneShotEffect {

    public WoodvineElementalEffect() {
        super(Outcome.Benefit);
    }

    private WoodvineElementalEffect(final WoodvineElementalEffect effect) {
        super(effect);
    }

    @Override
    public WoodvineElementalEffect copy() {
        return new WoodvineElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int parley = ParleyCount.getInstance().calculate(game, source, this);
            if (parley > 0) {
                game.addEffect(new BoostControlledEffect(parley, parley, Duration.EndOfTurn, new FilterAttackingCreature("Attacking creatures"), false), source);
            }
            return true;
        }
        return false;
    }
}