package mage.cards.d;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FreerunningAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.HumanRogueToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DistractTheGuards extends CardImpl {

    public DistractTheGuards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Freerunning {1}{W}
        this.addAbility(new FreerunningAbility("{1}{W}"));

        // Create three 1/1 white Human Rogue creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new HumanRogueToken()));
    }

    private DistractTheGuards(final DistractTheGuards card) {
        super(card);
    }

    @Override
    public DistractTheGuards copy() {
        return new DistractTheGuards(this);
    }
}
