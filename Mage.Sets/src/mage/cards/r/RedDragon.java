package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RedDragon extends CardImpl {

    public RedDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Fire Breath — When Red Dragon enters the battlefield, it deals 4 damage to each opponent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamagePlayersEffect(
                4, TargetController.OPPONENT, "it"
        )).withFlavorWord("Fire Breath"));
    }

    private RedDragon(final RedDragon card) {
        super(card);
    }

    @Override
    public RedDragon copy() {
        return new RedDragon(this);
    }
}
