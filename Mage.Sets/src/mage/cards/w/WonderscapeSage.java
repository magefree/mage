package mage.cards.w;

import java.util.List;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 * @author grimreap124
 */
public final class WonderscapeSage extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();
    private static final FilterControlledPermanent basicLandFilter = new FilterControlledLandPermanent();

    static {
        basicLandFilter.add(SuperType.BASIC.getPredicate());
    }

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
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)));
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
        new DrawCardSourceControllerEffect(1).apply(game, source);
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Cost cost :source.getCosts()) {
            if (cost instanceof ReturnToHandChosenControlledPermanentCost) {
                List<Permanent> permanents = ((ReturnToHandChosenControlledPermanentCost) cost).getPermanents();
                if (permanents.size() == 1) {
                    Permanent permanent = permanents.get(0);
                    if (permanent.getCardType().contains(CardType.LAND) && permanent.getSubtype().stream()
                            .filter(subType -> !(subType == SubType.PLAINS || subType == SubType.ISLAND || subType == SubType.SWAMP
                                    || subType == SubType.MOUNTAIN || subType == SubType.FOREST)).count() > 0) {
                            return true;
                    }
                }
            }
                player.discardOne(false, false, source, game);
                return true;
        }

        return true;
    }
}