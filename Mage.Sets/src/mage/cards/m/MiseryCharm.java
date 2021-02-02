
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LoneFox
 */
public final class MiseryCharm extends CardImpl {

    private static final FilterPermanent filter1 = new FilterPermanent("Cleric");
    private static final FilterCard filter2 = new FilterCard("Cleric card from your graveyard");

    static {
        filter1.add(SubType.CLERIC.getPredicate());
        filter2.add(SubType.CLERIC.getPredicate());
    }

    public MiseryCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Choose one - Destroy target Cleric
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter1));
        // or return target Cleric card from your graveyard to your hand
        Mode mode = new Mode();
        mode.addEffect(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filter2));
        this.getSpellAbility().addMode(mode);
        // or target player loses 2 life.
        mode = new Mode();
        mode.addEffect(new LoseLifeTargetEffect(2));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private MiseryCharm(final MiseryCharm card) {
        super(card);
    }

    @Override
    public MiseryCharm copy() {
        return new MiseryCharm(this);
    }
}
