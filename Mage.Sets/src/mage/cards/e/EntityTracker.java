package mage.cards.e;

import mage.MageInt;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EntityTracker extends CardImpl {

    public EntityTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Eerie -- Whenever an enchantment you control enters and whenever you fully unlock a Room, draw a card.
        this.addAbility(new EerieAbility(new DrawCardSourceControllerEffect(1)));
    }

    private EntityTracker(final EntityTracker card) {
        super(card);
    }

    @Override
    public EntityTracker copy() {
        return new EntityTracker(this);
    }
}
