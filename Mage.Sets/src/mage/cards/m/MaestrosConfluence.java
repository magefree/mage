package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.MonocoloredPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaestrosConfluence extends CardImpl {

    private static final FilterCard filter
            = new FilterInstantOrSorceryCard("monocolored instant or sorcery card from your graveyard");

    static {
        filter.add(MonocoloredPredicate.instance);
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
        this.getSpellAbility().addMode(new Mode(new MaestrosConfluenceEffect()).addTarget(new TargetPlayer()));
    }

    private MaestrosConfluence(final MaestrosConfluence card) {
        super(card);
    }

    @Override
    public MaestrosConfluence copy() {
        return new MaestrosConfluence(this);
    }
}

class MaestrosConfluenceEffect extends OneShotEffect {

    MaestrosConfluenceEffect() {
        super(Outcome.Benefit);
        staticText = "goad each creature target player controls";
    }

    private MaestrosConfluenceEffect(final MaestrosConfluenceEffect effect) {
        super(effect);
    }

    @Override
    public MaestrosConfluenceEffect copy() {
        return new MaestrosConfluenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source.getFirstTarget(), source, game
        )) {
            game.addEffect(new GoadTargetEffect().setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
