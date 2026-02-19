package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FootNinjas extends CardImpl {

    public FootNinjas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W/B}{W/B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Sneak {3}{W/B}
        this.addAbility(new SneakAbility(this, "{3}{W/B}"));

        // When this creature enters, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));
    }

    private FootNinjas(final FootNinjas card) {
        super(card);
    }

    @Override
    public FootNinjas copy() {
        return new FootNinjas(this);
    }
}
