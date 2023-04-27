package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.Elemental44Token;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElementalMasterpiece extends CardImpl {

    public ElementalMasterpiece(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}{R}");

        // Create two 4/4 blue and red Elemental creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Elemental44Token(), 2));

        // {U/R}{U/R}, Discard Elemental Masterpiece: Create a Treasure token.
        Ability ability = new SimpleActivatedAbility(
                Zone.HAND, new CreateTokenEffect(new TreasureToken()), new ManaCostsImpl<>("{U/R}{U/R}")
        );
        ability.addCost(new DiscardSourceCost());
        this.addAbility(ability);
    }

    private ElementalMasterpiece(final ElementalMasterpiece card) {
        super(card);
    }

    @Override
    public ElementalMasterpiece copy() {
        return new ElementalMasterpiece(this);
    }
}
