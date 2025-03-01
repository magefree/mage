package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MayCastFromGraveyardSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;

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
        Ability ability = new SimpleStaticAbility(Zone.GRAVEYARD, new WickerfolkIndomitableGraveyardEffect());
        ability.addCost(new PayLifeCost(2).setText(""));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE)
                .setText(""));

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
        if (!objectId.equals(source.getSourceId()) || !source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        return card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD;
    }
}
