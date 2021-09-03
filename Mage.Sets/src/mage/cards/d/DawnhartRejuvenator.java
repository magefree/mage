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
public final class DawnhartRejuvenator extends CardImpl {

    public DawnhartRejuvenator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Dawnhart Rejuvenator enters the battlefield, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private DawnhartRejuvenator(final DawnhartRejuvenator card) {
        super(card);
    }

    @Override
    public DawnhartRejuvenator copy() {
        return new DawnhartRejuvenator(this);
    }
}
