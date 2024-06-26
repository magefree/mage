package mage.cards.u;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.TwoOrMoreOpponentsCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UndergrowthStadium extends CardImpl {

    public UndergrowthStadium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Undergrowth Stadium enters the battlefield tapped unless you have two or more opponents.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(TwoOrMoreOpponentsCondition.instance));

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private UndergrowthStadium(final UndergrowthStadium card) {
        super(card);
    }

    @Override
    public UndergrowthStadium copy() {
        return new UndergrowthStadium(this);
    }
}
