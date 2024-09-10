package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class AvatarOfHope extends CardImpl {

    public AvatarOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{W}{W}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(9);

        // If you have 3 or less life, Avatar of Hope costs {6} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(6, AvatarOfHopeCondition.instance)
                .setText("if you have 3 or less life, this spell costs {6} less to cast"))
                .addHint(new ConditionHint(AvatarOfHopeCondition.instance))
        );

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Avatar of Hope can block any number of creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureEffect(0)));
    }

    private AvatarOfHope(final AvatarOfHope card) {
        super(card);
    }

    @Override
    public AvatarOfHope copy() {
        return new AvatarOfHope(this);
    }
}

enum AvatarOfHopeCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getLife() <= 3) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "you have 3 or less life";
    }
}
