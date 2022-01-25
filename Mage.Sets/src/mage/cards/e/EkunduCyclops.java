package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.common.combat.AttacksIfAbleSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public final class EkunduCyclops extends CardImpl {
    public EkunduCyclops(UUID ownerID, CardSetInfo setInfo) {
        super(ownerID, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.CYCLOPS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // If a creature you control attacks, Ekundu Cyclops also attacks if able.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(new AttacksIfAbleSourceEffect(Duration.EndOfTurn, true)));
    }

    private EkunduCyclops(final EkunduCyclops card) { super(card); }

    @Override
    public Card copy() { return new EkunduCyclops(this); }
}
