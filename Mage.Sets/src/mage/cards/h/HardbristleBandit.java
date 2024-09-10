package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HardbristleBandit extends CardImpl {

    public HardbristleBandit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Whenever you commit a crime, untap Hardbristle Bandit. This ability triggers only once each turn.
        this.addAbility(new CommittedCrimeTriggeredAbility(new UntapSourceEffect()).setTriggersLimitEachTurn(1));
    }

    private HardbristleBandit(final HardbristleBandit card) {
        super(card);
    }

    @Override
    public HardbristleBandit copy() {
        return new HardbristleBandit(this);
    }
}
