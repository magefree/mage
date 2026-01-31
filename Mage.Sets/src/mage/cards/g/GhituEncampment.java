
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author North
 */
public final class GhituEncampment extends CardImpl {

    public GhituEncampment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new RedManaAbility());
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(
                    2, 1, "2/1 red Warrior creature with first strike", SubType.WARRIOR
                ).withColor("R").withAbility(FirstStrikeAbility.getInstance()),
                CardType.LAND,
                Duration.EndOfTurn
            ),
            new ManaCostsImpl<>("{1}{R}")
        ));
    }

    private GhituEncampment(final GhituEncampment card) {
        super(card);
    }

    @Override
    public GhituEncampment copy() {
        return new GhituEncampment(this);
    }
}
