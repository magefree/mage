package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawnheartRejuvenator extends CardImpl {

    public DawnheartRejuvenator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Dawnheart Rejuvenator enters the battlefield, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private DawnheartRejuvenator(final DawnheartRejuvenator card) {
        super(card);
    }

    @Override
    public DawnheartRejuvenator copy() {
        return new DawnheartRejuvenator(this);
    }
}
