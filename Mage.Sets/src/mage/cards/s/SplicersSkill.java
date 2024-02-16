package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.PhyrexianGolemToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SplicersSkill extends CardImpl {

    public SplicersSkill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Create a 3/3 colorless Golem artifact creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PhyrexianGolemToken()));

        // Splice onto instant or sorcery {3}{W}
        this.addAbility(new SpliceAbility(SpliceAbility.INSTANT_OR_SORCERY, "{3}{W}"));
    }

    private SplicersSkill(final SplicersSkill card) {
        super(card);
    }

    @Override
    public SplicersSkill copy() {
        return new SplicersSkill(this);
    }
}
