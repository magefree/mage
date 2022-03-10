package mage.cards.p;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.game.permanent.token.PlanewideCelebrationToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlanewideCelebration extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public PlanewideCelebration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}{G}");

        // Choose four. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(4);
        this.getSpellAbility().getModes().setMaxModes(4);
        this.getSpellAbility().getModes().setEachModeMoreThanOnce(true);

        // • Create a 2/2 Citizen creature token that's all colors.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PlanewideCelebrationToken()));

        // • Return target permanent card from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addMode(mode);

        // • Proliferate.
        this.getSpellAbility().addMode(new Mode(new ProliferateEffect(false)));

        // • You gain 4 life.
        this.getSpellAbility().addMode(new Mode(new GainLifeEffect(4)));
    }

    private PlanewideCelebration(final PlanewideCelebration card) {
        super(card);
    }

    @Override
    public PlanewideCelebration copy() {
        return new PlanewideCelebration(this);
    }
}
