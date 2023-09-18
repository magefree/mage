

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BogardanHellkite extends CardImpl {

    public BogardanHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlashAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageMultiEffect(5, "it"), false);
        ability.addTarget(new TargetAnyTargetAmount(5));
        this.addAbility(ability);
    }

    private BogardanHellkite(final BogardanHellkite card) {
        super(card);
    }

    @Override
    public BogardanHellkite copy() {
        return new BogardanHellkite(this);
    }

}
