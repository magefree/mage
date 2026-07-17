package mage.cards.m;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.Elemental33BlueRedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MusesEncouragement extends CardImpl {

    public MusesEncouragement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // Create a 3/3 blue and red Elemental creature token with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Elemental33BlueRedToken()));

        // Surveil 2.
        this.getSpellAbility().addEffect(new SurveilEffect(2).concatBy("<br>"));
    }

    private MusesEncouragement(final MusesEncouragement card) {
        super(card);
    }

    @Override
    public MusesEncouragement copy() {
        return new MusesEncouragement(this);
    }
}
