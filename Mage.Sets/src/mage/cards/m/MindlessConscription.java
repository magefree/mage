package mage.cards.m;

import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MindlessConscription extends CardImpl {

    public MindlessConscription(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // When Mindless Conscription enters the battlefield and whenever you draw your third card each turn, amass Zombies 3.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD,
                new AmassEffect(3, SubType.ZOMBIE),
                false, "When {this} enters the battlefield and whenever you draw your third card each turn, ",
                new EntersBattlefieldTriggeredAbility(null),
                new DrawNthCardTriggeredAbility(null, false, 3)
        ));
    }

    private MindlessConscription(final MindlessConscription card) {
        super(card);
    }

    @Override
    public MindlessConscription copy() {
        return new MindlessConscription(this);
    }
}
