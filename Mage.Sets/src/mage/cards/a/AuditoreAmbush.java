package mage.cards.a;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author balazskristof
 */
public final class AuditoreAmbush extends CardImpl {

    private static final FilterCard filter = new FilterCard("Ezio, Blade of Vengeance");

    static {
        filter.add(new NamePredicate("Ezio, Blade of Vengeance"));
    }

    public AuditoreAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}");
        

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Return target creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // * Target player searches their library and/or graveyard for a card named Ezio, Blade of Vengeance, reveals it, and puts it into their hand. If they search their library this way, they shuffle.
        Mode mode = new Mode(new SearchLibraryGraveyardPutInHandTargetPlayerEffect(filter, false));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private AuditoreAmbush(final AuditoreAmbush card) {
        super(card);
    }

    @Override
    public AuditoreAmbush copy() {
        return new AuditoreAmbush(this);
    }
}
