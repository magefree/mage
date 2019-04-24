
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
import mage.filter.predicate.mageobject.SubtypePredicate;
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
        filter1.add(new SubtypePredicate(SubType.CLERIC));
        filter2.add(new SubtypePredicate(SubType.CLERIC));
    }

    public MiseryCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Choose one - Destroy target Cleric
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter1));
        // or return target Cleric card from your graveyard to your hand
        Mode mode = new Mode();
        mode.getEffects().add(new ReturnToHandTargetEffect());
        mode.getTargets().add(new TargetCardInYourGraveyard(filter2));
        this.getSpellAbility().addMode(mode);
        // or target player loses 2 life.
        mode = new Mode();
        mode.getEffects().add(new LoseLifeTargetEffect(2));
        mode.getTargets().add(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    public MiseryCharm(final MiseryCharm card) {
        super(card);
    }

    @Override
    public MiseryCharm copy() {
        return new MiseryCharm(this);
    }
}
