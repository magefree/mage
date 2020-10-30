package mage.cards.s;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.OneOpponentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpectatorSeating extends CardImpl {

    public SpectatorSeating(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Spectator Seating enters the battlefield tapped unless you have two or more opponents.
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(
                        new TapSourceEffect(),
                        OneOpponentCondition.instance,
                        "tapped unless you have two or more opponents"
                ), "tapped unless you have two or more opponents"
        ));

        // {T}: Add {R} or {W}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private SpectatorSeating(final SpectatorSeating card) {
        super(card);
    }

    @Override
    public SpectatorSeating copy() {
        return new SpectatorSeating(this);
    }
}
