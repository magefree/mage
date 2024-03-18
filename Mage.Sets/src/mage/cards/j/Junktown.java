package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.JunkToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Junktown extends CardImpl {

    public Junktown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}{R}, {T}, Sacrifice Junktown: Create three Junk tokens.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new JunkToken(), 3), new ManaCostsImpl<>("{4}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private Junktown(final Junktown card) {
        super(card);
    }

    @Override
    public Junktown copy() {
        return new Junktown(this);
    }
}
