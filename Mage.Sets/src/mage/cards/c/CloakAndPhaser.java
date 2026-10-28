package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.StrangeNewWorldsValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.common.StrangeNewWorldsHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.RomulanToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class CloakAndPhaser extends CardImpl {

    public CloakAndPhaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{8}{U}");

        // Strange new worlds -- This spell costs {X} less to cast, where X is the number of differently named lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(StrangeNewWorldsValue.instance))
            .setAbilityWord(AbilityWord.STRANGE_NEW_WORLDS).addHint(StrangeNewWorldsHint.instance));

        // Create two 2/2 black Romulan creature tokens. Draw two cards, then discard a card.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new RomulanToken(), 2));
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 1));
    }

    private CloakAndPhaser(final CloakAndPhaser card) {
        super(card);
    }

    @Override
    public CloakAndPhaser copy() {
        return new CloakAndPhaser(this);
    }
}
