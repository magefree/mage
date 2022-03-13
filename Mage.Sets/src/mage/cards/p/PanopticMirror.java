package mage.cards.p;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class PanopticMirror extends CardImpl {

    public PanopticMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Imprint - {X}, {tap}: You may exile an instant or sorcery card with converted mana cost X from your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PanopticMirrorExileEffect(), new VariableManaCost(VariableCostType.NORMAL));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.setAbilityWord(AbilityWord.IMPRINT));
        // At the beginning of your upkeep, you may copy a card exiled with Panoptic Mirror. If you do, you may cast the copy without paying its mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new PanopticMirrorCastEffect(), TargetController.YOU, true));
    }

    private PanopticMirror(final PanopticMirror card) {
        super(card);
    }

    @Override
    public PanopticMirror copy() {
        return new PanopticMirror(this);
    }
}

class PanopticMirrorExileEffect extends OneShotEffect {

    public PanopticMirrorExileEffect() {
        super(Outcome.Exile);
        this.staticText = "You may exile an instant or sorcery card with mana value X from your hand";
    }

    public PanopticMirrorExileEffect(final PanopticMirrorExileEffect effect) {
        super(effect);
    }

    @Override
    public PanopticMirrorExileEffect copy() {
        return new PanopticMirrorExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        source.getManaCostsToPay().getX();
        int count = source.getManaCostsToPay().getX();

        FilterInstantOrSorceryCard filter = new FilterInstantOrSorceryCard("instant or sorcery card with mana value equal to " + count);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, count));
        String choiceText = "Exile a " + filter.getMessage() + " from your hand?";

        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().count(filter, game) == 0
                || !player.chooseUse(this.outcome, choiceText, source, game)) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(filter);
        if (player.choose(Outcome.PlayForFree, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                card.moveToExile(CardUtil.getCardExileZoneId(game, source), "Panoptic Mirror", source, game);
                Permanent PanopticMirror = game.getPermanent(source.getSourceId());
                if (PanopticMirror != null) {
                    PanopticMirror.imprint(card.getId(), game);
                }
                return true;
            }
        }
        return false;
    }
}

class PanopticMirrorCastEffect extends OneShotEffect {

    public PanopticMirrorCastEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "you may copy a card exiled with {this}. If you do, you may cast the copy without paying its mana cost";
    }

    public PanopticMirrorCastEffect(final PanopticMirrorCastEffect effect) {
        super(effect);
    }

    @Override
    public PanopticMirrorCastEffect copy() {
        return new PanopticMirrorCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent PanopticMirror = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (PanopticMirror == null) {
            PanopticMirror = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (PanopticMirror != null
                && PanopticMirror.getImprinted() != null
                && !PanopticMirror.getImprinted().isEmpty()
                && controller != null) {
            CardsImpl cards = new CardsImpl();
            for (UUID uuid : PanopticMirror.getImprinted()) {
                Card card = game.getCard(uuid);
                if (card != null) {
                    if (card instanceof SplitCard) {
                        cards.add(((SplitCard) card).getLeftHalfCard());
                        cards.add(((SplitCard) card).getRightHalfCard());
                    } else if (card instanceof ModalDoubleFacesCard) {
                        cards.add(((ModalDoubleFacesCard) card).getLeftHalfCard());
                        cards.add(((ModalDoubleFacesCard) card).getRightHalfCard());
                    } else {
                        cards.add(card);
                    }
                }
            }
            Card cardToCopy;
            if (cards.size() == 1) {
                cardToCopy = cards.getCards(game).iterator().next();
            } else {
                TargetCard target = new TargetCard(1, Zone.EXILED, new FilterCard("card to copy"));
                controller.choose(Outcome.Copy, cards, target, game);
                cardToCopy = cards.get(target.getFirstTarget(), game);
            }
            if (cardToCopy != null) {
                Card copy = game.copyCard(cardToCopy, source, source.getControllerId());
                if (controller.chooseUse(Outcome.PlayForFree, "Cast the copied card without paying mana cost?", source, game)) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + copy.getId(), Boolean.TRUE);
                    controller.cast(controller.chooseAbilityForCast(copy, game, true),
                            game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + copy.getId(), null);
                }
            }
            return true;
        }
        return false;
    }
}
