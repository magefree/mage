package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author grimreap124, xenohedron
 */
public final class WonderscapeSage extends CardImpl {

    public WonderscapeSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        
        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {T}, Return a land you control to its owner's hand: Draw a card. Then discard a card unless that land had a nonbasic land type.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WonderscapeSageEffect(), new TapSourceCost());
        ability.addCost(new WonderscapeSageReturnCost());
        this.addAbility(ability);
    }

    private WonderscapeSage(final WonderscapeSage card) {
        super(card);
    }

    @Override
    public WonderscapeSage copy() {
        return new WonderscapeSage(this);
    }
}

class WonderscapeSageEffect extends OneShotEffect {

    WonderscapeSageEffect() {
        super(Outcome.Benefit);
        staticText = "Draw a card. Then discard a card unless that land had a nonbasic land type.";
    }

    private WonderscapeSageEffect(final WonderscapeSageEffect effect) {
        super(effect);
    }

    @Override
    public WonderscapeSageEffect copy() {
        return new WonderscapeSageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(1, source, game);
        if (!CardUtil
                .castStream(source.getCosts().stream(), WonderscapeSageReturnCost.class)
                .filter(Objects::nonNull)
                .map(WonderscapeSageReturnCost::getNonBasicLandTypes)
                .flatMap(Collection::stream)
                .distinct()
                .findAny()
                .isPresent()) {
            player.discardOne(false, false, source, game);
        }
        return true;
    }
}

class WonderscapeSageReturnCost extends ReturnToHandChosenControlledPermanentCost {

    private final Set<SubType> nonBasicLandTypes = new HashSet<>();

    WonderscapeSageReturnCost() {
        super(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        setText("Return a land you control to its owner's hand");
    }

    private WonderscapeSageReturnCost(final WonderscapeSageReturnCost cost) {
        super(cost);
        this.nonBasicLandTypes.addAll(cost.nonBasicLandTypes);
    }

    @Override
    public WonderscapeSageReturnCost copy() {
        return new WonderscapeSageReturnCost(this);
    }

    Set<SubType> getNonBasicLandTypes() {
        return this.nonBasicLandTypes;
    }

    @Override
    protected void addReturnTarget(Game game, Permanent permanent) {
        super.addReturnTarget(game, permanent);
        // The Permanent's types will lose the gained/lose types after the sacrifice, so they are stored right before.
        if (permanent.getCardType(game).contains(CardType.LAND)) {
            permanent.getSubtype(game)
                    .stream()
                    .filter(s -> s.getSubTypeSet().equals(SubTypeSet.NonBasicLandType))
                    .forEach(nonBasicLandTypes::add);
        }
    }
}
