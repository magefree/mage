
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class CrumblingColossus extends CardImpl {

    public CrumblingColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(7);
        this.toughness = new MageInt(4);

        this.addAbility(TrampleAbility.getInstance());

        this.addAbility(new AttacksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new SacrificeSourceEffect()))
                .setText("sacrifice it at end of combat"), false).setTriggerPhrase("When {this} attacks, "));
    }

    private CrumblingColossus(final CrumblingColossus card) {
        super(card);
    }

    @Override
    public CrumblingColossus copy() {
        return new CrumblingColossus(this);
    }
}
