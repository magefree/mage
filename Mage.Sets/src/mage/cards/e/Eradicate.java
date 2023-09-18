package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetAndSearchGraveyardHandLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Eradicate extends CardImpl {

    public Eradicate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        // Exile target nonblack creature. Search its controller's graveyard, hand, and library for all cards 
        // with the same name as that creature and exile them. Then that player shuffles their library.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.getSpellAbility().addEffect(new ExileTargetAndSearchGraveyardHandLibraryEffect(false, "its controller's","all cards with the same name as that creature"));
    }

    private Eradicate(final Eradicate card) {
        super(card);
    }

    @Override
    public Eradicate copy() {
        return new Eradicate(this);
    }
}
