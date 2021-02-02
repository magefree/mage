package mage.cards.t;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TriplicateSpirits extends CardImpl {

    public TriplicateSpirits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");


        // Convoke
        this.addAbility(new ConvokeAbility());
        // Create three 1/1 white Spirit creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiritWhiteToken(), 3));

    }

    private TriplicateSpirits(final TriplicateSpirits card) {
        super(card);
    }

    @Override
    public TriplicateSpirits copy() {
        return new TriplicateSpirits(this);
    }
}
