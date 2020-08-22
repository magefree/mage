package mage.cards.u;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.OneOpponentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
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
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(
                        new TapSourceEffect(),
                        OneOpponentCondition.instance,
                        "tapped unless you have two or more opponents"
                ), "tapped unless you have two or more opponents"
        ));

        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
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
