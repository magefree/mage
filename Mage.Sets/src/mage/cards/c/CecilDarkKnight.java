package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author balazskristof
 */
public final class CecilDarkKnight extends CardImpl {

    public CecilDarkKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.c.CecilRedeemedPaladin.class;

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Darkness -- Whenever Cecil deals damage, you lose that much life. Then if your life total is less than or equal to half your starting life total, untap Cecil and transform it.
        this.addAbility(new TransformAbility());
        Ability ability = new DealsDamageSourceTriggeredAbility(new LoseLifeSourceControllerEffect(SavedDamageValue.MUCH), false).withFlavorWord("Darkness");
        ability.addEffect(new ConditionalOneShotEffect(
                new UntapSourceEffect(),
                CecilDarkKnightCondition.instance
        ).addEffect(
                new TransformSourceEffect(true).concatBy("and")
        ).concatBy("Then"));
        this.addAbility(ability);
    }

    private CecilDarkKnight(final CecilDarkKnight card) {
        super(card);
    }

    @Override
    public CecilDarkKnight copy() {
        return new CecilDarkKnight(this);
    }
}

enum CecilDarkKnightCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(Player::getLife)
                .map(life -> life <= game.getStartingLife() / 2)
                .orElse(false);
    }

    @Override
    public String toString() {
        return "your life total is less than or equal to half your starting life total";
    }
}
