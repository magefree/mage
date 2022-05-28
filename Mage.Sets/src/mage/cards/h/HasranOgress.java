package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class HasranOgress extends CardImpl {

    public HasranOgress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Hasran Ogress attacks, it deals 3 damage to you unless you pay {2}.
        this.addAbility(new AttacksTriggeredAbility(new HasranOgressEffect(), false));
    }

    private HasranOgress(final HasranOgress card) {
        super(card);
    }

    @Override
    public HasranOgress copy() {
        return new HasranOgress(this);
    }
}

class HasranOgressEffect extends OneShotEffect {

    public HasranOgressEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 3 damage to you unless you pay {2}";
    }

    public HasranOgressEffect(final HasranOgressEffect effect) {
        super(effect);
    }

    @Override
    public HasranOgressEffect copy() {
        return new HasranOgressEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cost cost = new ManaCostsImpl<>("{2}");
            if (!(controller.chooseUse(Outcome.Benefit, "Pay {2}?", source, game)
                    && cost.pay(source, game, source, controller.getId(), false, null))) {
                controller.damage(3, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }
}

