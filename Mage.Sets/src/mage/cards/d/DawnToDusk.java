package mage.cards.d;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DawnToDusk extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("enchantment card from your graveyard");

    static {
        filterCard.add(CardType.ENCHANTMENT.getPredicate());
    }

    public DawnToDusk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");


        // Choose one or both -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Return target enchantment card from your graveyard to your hand;
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filterCard).withChooseHint("return from graveyard to hand"));
        // and/or destroy target enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent().withChooseHint("destroy"));
        this.getSpellAbility().addMode(mode);
    }

    private DawnToDusk(final DawnToDusk card) {
        super(card);
    }

    @Override
    public DawnToDusk copy() {
        return new DawnToDusk(this);
    }
}
