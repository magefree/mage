package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieBerserkerToken;

/**
 *
 * @author weirddan455
 */
public final class DeathknellBerserker extends CardImpl {

    public DeathknellBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Deathknell Berserker dies, if its power was 3 or greater, create a 2/2 black Zombie Berserker creature token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DiesSourceTriggeredAbility(new CreateTokenEffect(new ZombieBerserkerToken())),
                DeathknellBerserkerCondtion.instance,
                "When {this} dies, if its power was 3 or greater, create a 2/2 black Zombie Berserker creature token."
        ));
    }

    private DeathknellBerserker(final DeathknellBerserker card) {
        super(card);
    }

    @Override
    public DeathknellBerserker copy() {
        return new DeathknellBerserker(this);
    }
}

enum DeathknellBerserkerCondtion implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getEffects().get(0).getValue("permanentLeftBattlefield");
        return permanent != null && permanent.getPower().getValue() >= 3;
    }
}
