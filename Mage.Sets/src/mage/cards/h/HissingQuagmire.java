
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author fireshoes
 */
public final class HissingQuagmire extends CardImpl {

    public HissingQuagmire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Hissing Quagmire enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

        // {1}{B}{G}: Hissing Quagmire becomes a 2/2 black and green Elemental creature with deathtouch until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(
                    2, 2, "2/2 black and green Elemental creature with deathtouch", SubType.ELEMENTAL
                ).withColor("BG").withAbility(DeathtouchAbility.getInstance()),
                CardType.LAND,
                Duration.EndOfTurn
            ).setText("{this} becomes a 2/2 black and green Elemental creature with deathtouch until end of turn. It's still a land"),
            new ManaCostsImpl<>("{1}{B}{G}")
        ));
    }

    private HissingQuagmire(final HissingQuagmire card) {
        super(card);
    }

    @Override
    public HissingQuagmire copy() {
        return new HissingQuagmire(this);
    }
}
