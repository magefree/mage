package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieBerserkerToken;
import mage.util.CardUtil;

import java.util.UUID;

/**
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
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new ZombieBerserkerToken()))
                .withInterveningIf(DeathknellBerserkerCondtion.instance));
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
        return CardUtil
                .getEffectValueFromAbility(source, "permanentLeftBattlefield", Permanent.class)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .filter(x -> x >= 3)
                .isPresent();
    }

    @Override
    public String toString() {
        return "its power was 3 or greater";
    }
}
