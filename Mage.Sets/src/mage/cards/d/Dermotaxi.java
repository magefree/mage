package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.effects.common.CopyEffect;
import mage.game.permanent.PermanentCard;
import mage.util.functions.CopyApplier;

/**
 * @author TheElk801
 */
public final class Dermotaxi extends CardImpl {

    public Dermotaxi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Imprint — As Dermotaxi enters the battlefield, exile a creature card from a graveyard.
        this.addAbility(new EntersBattlefieldAbility(
                new DermotaxiImprintEffect(), null, "<i>Imprint</i> &mdash; As {this} "
                + "enters the battlefield, exile a creature card from a graveyard.", ""
        ));

        // Tap two untapped creatures you control: Until end of turn, Dermotaxi becomes a copy of the imprinted card, except it's a Vehicle artifact in addition to its other types.
        this.addAbility(new SimpleActivatedAbility(
                new DermotaxiCopyEffect(),
                new TapTargetCost(new TargetControlledPermanent(
                        2, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES
                ))
        ));
    }

    private Dermotaxi(final Dermotaxi card) {
        super(card);
    }

    @Override
    public Dermotaxi copy() {
        return new Dermotaxi(this);
    }
}

class DermotaxiImprintEffect extends OneShotEffect {

    DermotaxiImprintEffect() {
        super(Outcome.Benefit);
        staticText = "exile a creature card from a graveyard";
    }

    private DermotaxiImprintEffect(final DermotaxiImprintEffect effect) {
        super(effect);
    }

    @Override
    public DermotaxiImprintEffect copy() {
        return new DermotaxiImprintEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        TargetCardInGraveyard target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE);
        target.setNotTarget(true);
        if (!target.canChoose(source.getControllerId(), game)) {
            return false;
        }
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        permanent.imprint(card.getId(), game);
        return true;
    }
}

class DermotaxiCopyEffect extends OneShotEffect {

    DermotaxiCopyEffect() {
        super(Outcome.Benefit);
        staticText = "until end of turn, {this} becomes a copy of the exiled card, "
                + "except it's a Vehicle artifact in addition to its other types";
    }

    private DermotaxiCopyEffect(final DermotaxiCopyEffect effect) {
        super(effect);
    }

    @Override
    public DermotaxiCopyEffect copy() {
        return new DermotaxiCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent == null) {
            return false;
        }
        Card card = game.getCard(sourcePermanent.getImprinted().get(0));
        if (card == null) {
            return false;
        }
        Permanent newBluePrint = new PermanentCard(card, source.getControllerId(), game);
        newBluePrint.assignNewId();
        DermotaxiCopyApplier applier = new DermotaxiCopyApplier();
        applier.apply(game, newBluePrint, source, sourcePermanent.getId());
        CopyEffect copyEffect = new CopyEffect(Duration.EndOfTurn, newBluePrint, sourcePermanent.getId());
        copyEffect.newId();
        copyEffect.setApplier(applier);
        game.addEffect(copyEffect, source);
        return true;
    }
}

class DermotaxiCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.addCardType(CardType.ARTIFACT);
        blueprint.addSubType(SubType.VEHICLE);
        return true;
    }
}
