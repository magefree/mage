
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class TendrilsOfAgony extends CardImpl {

    public TendrilsOfAgony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Target player loses 2 life and you gain 2 life.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2));
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("and"));
        // Storm (When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.)
        this.addAbility(new StormAbility());
    }

    private TendrilsOfAgony(final TendrilsOfAgony card) {
        super(card);
    }

    @Override
    public TendrilsOfAgony copy() {
        return new TendrilsOfAgony(this);
    }
}
