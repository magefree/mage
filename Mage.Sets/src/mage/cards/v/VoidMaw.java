
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.replacement.CreaturesAreExiledOnDeathReplacementEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

/**
 * @author jeffwadsworth & L_J
 */
public final class VoidMaw extends CardImpl {

    public VoidMaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // If another creature would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(
            new CreaturesAreExiledOnDeathReplacementEffect(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE)
        ));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VoidMawEffect()));

        // Put a card exiled with Void Maw into its owner's graveyard: Void Maw gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn), new VoidMawCost()));
    }

    private VoidMaw(final VoidMaw card) {
        super(card);
    }

    @Override
    public VoidMaw copy() {
        return new VoidMaw(this);
    }
}

class VoidMawCost extends CostImpl {

    public VoidMawCost() {
        this.text = "Put a card exiled with {this} into its owner's graveyard";
    }

    public VoidMawCost(VoidMawCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            TargetCardInExile target = new TargetCardInExile(new FilterCard(), CardUtil.getCardExileZoneId(game, ability));
            target.setNotTarget(true);
            Cards cards = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, ability));
            if (cards != null
                    && !cards.isEmpty()
                    && controller.choose(Outcome.Benefit, cards, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    if (controller.moveCardToGraveyardWithInfo(card, source, game, Zone.EXILED)) {
                        paid = true;
                    }
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        return player != null;
    }

    @Override
    public VoidMawCost copy() {
        return new VoidMawCost(this);
    }
}
