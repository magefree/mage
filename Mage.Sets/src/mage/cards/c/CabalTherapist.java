package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CabalTherapist extends CardImpl {

    public CabalTherapist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // At the beginning of your precombat main phase, you may sacrifice a creature. When you do, choose a nonland card name, then target player reveals their hand and discards all cards with that name.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NON_LAND_NAME),
                false, "choose a nonland card name, then target player " +
                "reveals their hand and discards all cards with that name"
        );
        ability.addEffect(new CabalTherapistDiscardEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                new DoWhenCostPaid(ability, new SacrificeTargetCost(
                        new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
                ), "Sacrifice a creature?"), TargetController.YOU, false
        ));
    }

    private CabalTherapist(final CabalTherapist card) {
        super(card);
    }

    @Override
    public CabalTherapist copy() {
        return new CabalTherapist(this);
    }
}

class CabalTherapistDiscardEffect extends OneShotEffect {

    CabalTherapistDiscardEffect() {
        super(Outcome.Discard);
    }

    private CabalTherapistDiscardEffect(final CabalTherapistDiscardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (targetPlayer == null || controller == null || sourceObject == null || cardName == null) {
            return false;
        }
        Cards hand = targetPlayer.getHand().copy();
        targetPlayer.revealCards(source, hand, game);
        hand.removeIf(uuid -> {
            Card card = hand.get(uuid, game);
            if (card == null) {
                return true;
            }
            return !CardUtil.haveSameNames(card, cardName, game);
        });
        targetPlayer.discard(hand, false, source, game);
        return true;
    }

    @Override
    public CabalTherapistDiscardEffect copy() {
        return new CabalTherapistDiscardEffect(this);
    }
}
