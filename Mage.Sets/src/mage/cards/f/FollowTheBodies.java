package mage.cards.f;

import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.GravestormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class FollowTheBodies extends CardImpl {

    public FollowTheBodies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Gravestorm
        this.addAbility(new GravestormAbility());

        // Investigate
        this.getSpellAbility().addEffect(new InvestigateEffect());
    }

    private FollowTheBodies(final FollowTheBodies card) {
        super(card);
    }

    @Override
    public FollowTheBodies copy() {
        return new FollowTheBodies(this);
    }
}
