package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.combat.GoadAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.MonocoloredPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaestrosConfluence extends CardImpl {

    private static final FilterCard filter
            = new FilterInstantOrSorceryCard("monocolored instant or sorcery card from your graveyard");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent();

    static {
        filter.add(MonocoloredPredicate.instance);
        filter2.add(TargetController.SOURCE_TARGETS.getControllerPredicate());
    }

    public MaestrosConfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{B}{R}");

        // Choose three. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(3);
        this.getSpellAbility().getModes().setMaxModes(3);
        this.getSpellAbility().getModes().setEachModeMoreThanOnce(true);

        // • Return target monocolored instant or sorcery card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        // • Target creature gets -3/-3 until end of turn.
        this.getSpellAbility().addMode(new Mode(
                new BoostTargetEffect(-3, -3)
        ).addTarget(new TargetCreaturePermanent()));

        // • Goad each creature target player controls.
        this.getSpellAbility().addMode(new Mode(new GoadAllEffect(filter2).setText("goad each creature target player controls")).addTarget(new TargetPlayer()));
    }

    private MaestrosConfluence(final MaestrosConfluence card) {
        super(card);
    }

    @Override
    public MaestrosConfluence copy() {
        return new MaestrosConfluence(this);
    }
}
