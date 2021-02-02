
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author fenhl
 */
public final class GlintEyeNephilim extends CardImpl {

    public GlintEyeNephilim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}{R}{G}");
        this.subtype.add(SubType.NEPHILIM);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Glint-Eye Nephilim deals combat damage to a player, draw that many cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new GlintEyeNephilimEffect(), false, true));

        // {1}, Discard a card: Glint-Eye Nephilim gets +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new GenericManaCost(1));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);

    }

    private GlintEyeNephilim(final GlintEyeNephilim card) {
        super(card);
    }

    @Override
    public GlintEyeNephilim copy() {
        return new GlintEyeNephilim(this);
    }
}

class GlintEyeNephilimEffect extends OneShotEffect {

    public GlintEyeNephilimEffect() {
        super(Outcome.DrawCard);
        this.staticText = "draw that many cards";
    }

    public GlintEyeNephilimEffect(final GlintEyeNephilimEffect effect) {
        super(effect);
    }

    @Override
    public GlintEyeNephilimEffect copy() {
        return new GlintEyeNephilimEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.drawCards(amount, source, game);
                return true;
            }
        }
        return false;
    }
}
