package mage.cards.r;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.OneOpponentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RejuvenatingSprings extends CardImpl {

    public RejuvenatingSprings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Rejuvenating Springs enters the battlefield tapped unless you have two or more opponents.
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(
                        new TapSourceEffect(),
                        OneOpponentCondition.instance,
                        "tapped unless you have two or more opponents"
                ), "tapped unless you have two or more opponents"
        ));

        // {T}: Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private RejuvenatingSprings(final RejuvenatingSprings card) {
        super(card);
    }

    @Override
    public RejuvenatingSprings copy() {
        return new RejuvenatingSprings(this);
    }
}
