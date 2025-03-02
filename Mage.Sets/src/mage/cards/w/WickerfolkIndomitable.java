package mage.cards.w;

import java.util.UUID;

import mage.MageIdentifier;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MayCastFromGraveyardSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Jmlundeen
 */
public final class WickerfolkIndomitable extends CardImpl {

    public WickerfolkIndomitable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // You may cast this card from your graveyard by paying 2 life and sacrificing an artifact or creature in addition to paying its other costs.
        Ability ability = new SimpleStaticAbility(Zone.GRAVEYARD, new WickerfolkIndomitableGraveyardEffect())
                .setIdentifier(MageIdentifier.WickerfolkIndomitableAlternateCast);

        this.addAbility(ability);
    }

    private WickerfolkIndomitable(final WickerfolkIndomitable card) {
        super(card);
    }

    @Override
    public WickerfolkIndomitable copy() {
        return new WickerfolkIndomitable(this);
    }
}

class WickerfolkIndomitableGraveyardEffect extends AsThoughEffectImpl {

    WickerfolkIndomitableGraveyardEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.PutCreatureInPlay);
        this.staticText = "You may cast this card from your graveyard by paying 2 life and sacrificing an artifact or creature in addition to paying its other costs.";
    }

    private WickerfolkIndomitableGraveyardEffect(final WickerfolkIndomitableGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public WickerfolkIndomitableGraveyardEffect copy() {
        return new WickerfolkIndomitableGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!objectId.equals(source.getSourceId()) || !source.isControlledBy(affectedControllerId)
        || game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return false;
        }
        Player controller = game.getPlayer(affectedControllerId);
        if (controller != null) {
            Costs<Cost> costs = new CostsImpl<>();
            costs.add(new PayLifeCost(2));
            costs.add(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE));
            controller.setCastSourceIdWithAlternateMana(objectId, new ManaCostsImpl<>("{3}{B}"), costs,
                    MageIdentifier.WickerfolkIndomitableAlternateCast);
            return true;
        }
        return false;
    }
}
