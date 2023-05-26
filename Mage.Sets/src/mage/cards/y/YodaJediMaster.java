package mage.cards.y;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.command.emblems.YodaEmblem;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public final class YodaJediMaster extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent you own");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public YodaJediMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.YODA);

        this.setStartingLoyalty(3);

        // +1: Look at the top two cards of your library. Put one on the bottom of your library.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(2, 1, PutCards.BOTTOM_ANY, PutCards.TOP_ANY), 1));

        //  0: Exile another target permanent you own. Return that card to the battlefield under your control at the beggining of your next end step.
        Ability ability = new LoyaltyAbility(new YodaJediMasterEffect(), 0);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // -5: You get an emblem with "Hexproof, you and your creatures have."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new YodaEmblem()), -5));

    }

    private YodaJediMaster(final YodaJediMaster card) {
        super(card);
    }

    @Override
    public YodaJediMaster copy() {
        return new YodaJediMaster(this);
    }
}

class YodaJediMasterEffect extends OneShotEffect {

    public YodaJediMasterEffect() {
        super(Outcome.Detriment);
        staticText = "Exile another target permanent you own. Return that card to the battlefield under your control at the beginning of your next end step";
    }

    public YodaJediMasterEffect(final YodaJediMasterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent != null && sourcePermanent != null) {
            if (permanent.moveToExile(source.getSourceId(), sourcePermanent.getName(), source, game)) {
                //create delayed triggered ability
                Effect effect = new ReturnToBattlefieldUnderYourControlTargetEffect();
                effect.setText("Return that card to the battlefield under your control at the beginning of your next end step");
                effect.setTargetPointer(new FixedTarget(permanent.getId(), game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public YodaJediMasterEffect copy() {
        return new YodaJediMasterEffect(this);
    }
}
