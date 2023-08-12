
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public final class LurkingEvil extends CardImpl {

    public LurkingEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}{B}{B}");

        // Pay half your life, rounded up: Lurking Evil becomes a 4/4 Horror creature with flying.
        Effect effect = new BecomesCreatureSourceEffect(new LurkingEvilToken(), null, Duration.EndOfGame);
        effect.setText("{this} becomes a 4/4 Phyrexian Horror creature with flying");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new LurkingEvilCost()));
    }

    private LurkingEvil(final LurkingEvil card) {
        super(card);
    }

    @Override
    public LurkingEvil copy() {
        return new LurkingEvil(this);
    }
}

class LurkingEvilCost extends CostImpl {

    LurkingEvilCost() {
        this.text = "Pay half your life, rounded up";
    }

    LurkingEvilCost(LurkingEvilCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        return controller != null && (controller.getLife() < 1 || controller.canPayLifeCost(ability));
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }

        int currentLife = controller.getLife();
        int lifeToPay = Math.max(0, (currentLife + currentLife % 2) / 2); // Divide by two and round up.
        if (lifeToPay <= 0) {
            this.paid = true;
        } else {
            this.paid = CardUtil.tryPayLife(lifeToPay, controller, source, game);
        }
        return this.paid;
    }

    @Override
    public LurkingEvilCost copy() {
        return new LurkingEvilCost(this);
    }
}

class LurkingEvilToken extends TokenImpl {

    LurkingEvilToken() {
        super("Phyrexian Horror", "4/4 Phyrexian Horror creature with flying");
        power = new MageInt(4);
        toughness = new MageInt(4);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.HORROR);
        cardType.add(CardType.CREATURE);
        this.addAbility(FlyingAbility.getInstance());
    }
    public LurkingEvilToken(final LurkingEvilToken token) {
        super(token);
    }

    public LurkingEvilToken copy() {
        return new LurkingEvilToken(this);
    }
}
