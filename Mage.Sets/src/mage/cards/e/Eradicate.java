
package mage.cards.e;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.ExileTargetAndSearchGraveyardHandLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Eradicate extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public Eradicate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");


        // Exile target nonblack creature. Search its controller's graveyard, hand, and library for all cards 
        // with the same name as that creature and exile them. Then that player shuffles their library.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new ExileTargetAndSearchGraveyardHandLibraryEffect(false, "its controller's","all cards with the same name as that creature"));
    }

    public Eradicate(final Eradicate card) {
        super(card);
    }

    @Override
    public Eradicate copy() {
        return new Eradicate(this);
    }
}
    
 