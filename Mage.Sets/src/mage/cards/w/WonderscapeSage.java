package mage.cards.w;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.costs.CostImpl;
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
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.util.CardUtil;

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
        ability.addCost(new WonderscapeSageCost(new TargetControlledPermanent(filter)));
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
            if (cost instanceof WonderscapeSageCost) {
                if (((WonderscapeSageCost) cost).nonbasic) {
                    return true;
                }
                player.discardOne(false, false, source, game);
                return true;
            }
        }
        return true;
    }
}

class WonderscapeSageCost extends CostImpl {

    protected boolean nonbasic = false;

    public WonderscapeSageCost(TargetControlledPermanent target) {
        target.withNotTarget(true);
        this.addTarget(target);
        if (target.getMaxNumberOfTargets() > 1 && target.getMaxNumberOfTargets() == target.getNumberOfTargets()) {
            this.text = "return " + CardUtil.numberToText(target.getMaxNumberOfTargets()) + ' '
                    + target.getTargetName()
                    + (target.getTargetName().endsWith(" you control") ? "" : " you control")
                    + " to their owner's hand";
        } else {
            this.text = "return " + CardUtil.addArticle(target.getTargetName())
                    + (target.getTargetName().endsWith(" you control") ? "" : " you control")
                    + " to its owner's hand";
        }
    }

    private WonderscapeSageCost(final WonderscapeSageCost cost) {
        super(cost);
        this.nonbasic = cost.nonbasic;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (this.getTargets().choose(Outcome.ReturnToHand, controllerId, source.getSourceId(), source, game)) {
                Set<Card> permanentsToReturn = new HashSet<>();
                for (UUID targetId : this.getTargets().get(0).getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent == null) {
                        return false;
                    }
                    permanentsToReturn.add(permanent);
                    if (permanent.getCardType().contains(CardType.LAND) && permanent.getSubtype().stream()
                            .filter(subType -> !(subType == SubType.PLAINS || subType == SubType.ISLAND || subType == SubType.SWAMP || subType == SubType.MOUNTAIN || subType == SubType.FOREST)).count() > 0){
                        nonbasic = true;
                    }
                }
                controller.moveCards(permanentsToReturn, Zone.HAND, ability, game);
                paid = true;
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return this.getTargets().canChoose(controllerId, source, game);
    }

    @Override
    public WonderscapeSageCost copy() {
        return new WonderscapeSageCost(this);
    }

}
