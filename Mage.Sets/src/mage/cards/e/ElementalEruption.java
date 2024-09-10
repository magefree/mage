package mage.cards.e;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.DragonElementalToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElementalEruption extends CardImpl {

    public ElementalEruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Create a 4/4 red Dragon Elemental creature token with flying and prowess.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new DragonElementalToken()));

        // Storm
        this.addAbility(new StormAbility());
    }

    private ElementalEruption(final ElementalEruption card) {
        super(card);
    }

    @Override
    public ElementalEruption copy() {
        return new ElementalEruption(this);
    }
}
