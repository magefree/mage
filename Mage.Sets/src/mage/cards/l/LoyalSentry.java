package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BlocksCreatureTriggeredAbility;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class LoyalSentry extends CardImpl {

    public LoyalSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Loyal Sentry blocks a creature, destroy that creature and Loyal Sentry.
        TriggeredAbility ability = new BlocksCreatureTriggeredAbility(new DestroyTargetEffect().setText("destroy that creature"));
        ability.addEffect(new DestroySourceEffect().setText("and {this}"));
        ability.setTriggerPhrase("When {this} blocks a creature, ");
        this.addAbility(ability);
    }

    private LoyalSentry(final LoyalSentry card) {
        super(card);
    }

    @Override
    public LoyalSentry copy() {
        return new LoyalSentry(this);
    }
}
