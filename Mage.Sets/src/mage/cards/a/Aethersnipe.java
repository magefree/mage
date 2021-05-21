
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class Aethersnipe extends CardImpl {

    public Aethersnipe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Aethersnipe enters the battlefield, return target nonland permanent to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        Target target = new TargetNonlandPermanent();
        ability.addTarget(target);
        this.addAbility(ability);

        // Evoke {1}{U}{U}
        this.addAbility(new EvokeAbility("{1}{U}{U}"));
    }

    private Aethersnipe(final Aethersnipe card) {
        super(card);
    }

    @Override
    public Aethersnipe copy() {
        return new Aethersnipe(this);
    }
}
