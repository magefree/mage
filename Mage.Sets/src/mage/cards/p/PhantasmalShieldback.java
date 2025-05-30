package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class PhantasmalShieldback extends CardImpl {

    public PhantasmalShieldback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Phantasmal Shieldback becomes the target of a spell or ability, sacrifice it.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(new SacrificeSourceEffect()));

        // When Phantasmal Shieldback dies, draw a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    private PhantasmalShieldback(final PhantasmalShieldback card) {
        super(card);
    }

    @Override
    public PhantasmalShieldback copy() {
        return new PhantasmalShieldback(this);
    }
}
