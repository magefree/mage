
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author TheElk801
 */
public final class BurningSunsAvatar extends CardImpl {

    public BurningSunsAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Burning Sun's Avatar enters the battlefield, it deals 3 damage to target opponent and 3 damage to up to one target creature.
        Effect effect = new DamageTargetEffect(3);
        effect.setText("it deals 3 damage to target opponent or planeswalker and 3 damage to up to one target creature");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private BurningSunsAvatar(final BurningSunsAvatar card) {
        super(card);
    }

    @Override
    public BurningSunsAvatar copy() {
        return new BurningSunsAvatar(this);
    }
}
