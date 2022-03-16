package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.WardAbility;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class IymrithDesertDoom extends CardImpl {

    public IymrithDesertDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Iymrith, Desert Doom has ward {4} as long as it's untapped.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new WardAbility(new GenericManaCost(4)), Duration.WhileOnBattlefield),
                SourceTappedCondition.UNTAPPED,
                "{this} has ward {4} as long as it's untapped"
        )));

        // Whenever Iymrith deals combat damage to a player, draw a card. Then if you have fewer than three cards in hand, draw cards equal to the difference.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), false);
        ability.addEffect(new IymrithDesertDoomEffect());
        this.addAbility(ability);
    }

    private IymrithDesertDoom(final IymrithDesertDoom card) {
        super(card);
    }

    @Override
    public IymrithDesertDoom copy() {
        return new IymrithDesertDoom(this);
    }
}

class IymrithDesertDoomEffect extends OneShotEffect {

    public IymrithDesertDoomEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Then if you have fewer than three cards in hand, draw cards equal to the difference";
    }

    private IymrithDesertDoomEffect(final IymrithDesertDoomEffect effect) {
        super(effect);
    }

    @Override
    public IymrithDesertDoomEffect copy() {
        return new IymrithDesertDoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int handSize = controller.getHand().size();
            if (handSize < 3) {
                controller.drawCards(3 - handSize, source, game);
                return true;
            }
        }
        return false;
    }
}
