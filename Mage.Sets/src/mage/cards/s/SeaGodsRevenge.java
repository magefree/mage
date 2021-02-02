package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SeaGodsRevenge extends CardImpl {

    public SeaGodsRevenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

        // Return up to three target creatures to their owners' hands. Scry 1.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent(0, 3));
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private SeaGodsRevenge(final SeaGodsRevenge card) {
        super(card);
    }

    @Override
    public SeaGodsRevenge copy() {
        return new SeaGodsRevenge(this);
    }
}
