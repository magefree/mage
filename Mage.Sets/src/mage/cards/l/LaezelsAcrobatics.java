package mage.cards.l;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;
import mage.util.ExileUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LaezelsAcrobatics extends CardImpl {

  public LaezelsAcrobatics(UUID ownerId, CardSetInfo setInfo) {
    super(ownerId, setInfo, new CardType[] { CardType.INSTANT }, "{3}{W}");

    this.getSpellAbility().addEffect(new LaezelsAcrobaticsEffect());
  }

  private LaezelsAcrobatics(final LaezelsAcrobatics card) {
    super(card);
  }

  @Override
  public LaezelsAcrobatics copy() {
    return new LaezelsAcrobatics(this);
  }
}

class LaezelsAcrobaticsEffect extends RollDieWithResultTableEffect {

  LaezelsAcrobaticsEffect() {
    super(20, "Exile all nontoken creatures you control, then roll a d20");
    this.addTableEntry(
        1, 9,
        new InfoEffect("Return those cards to the battlefield under their owner's control at the " +
            "beginning of the next end step."));
    this.addTableEntry(
        10, 20,
        new InfoEffect(
            "Return those cards to the battlefield under their owner's control, then exile them again. Return those cards to the battlefield under their owner's control at the beginning of the next end step."));
  }

  private LaezelsAcrobaticsEffect(final LaezelsAcrobaticsEffect effect) {
    super(effect);
  }

  @Override
  public LaezelsAcrobaticsEffect copy() {
    return new LaezelsAcrobaticsEffect(this);
  }

  private static final FilterControlledCreaturePermanent creatureFilter = new FilterControlledCreaturePermanent();

  static {
    creatureFilter.add(TokenPredicate.FALSE);
  }

  private Set<Card> getNontokenCreatureCards(Game game, Player player) {
    List<Permanent> playerPermanents = game
        .getState()
        .getBattlefield()
        .getActivePermanents(creatureFilter, player.getId(), game);

    Set<Card> toExile = new HashSet<>();

    for (Permanent permanent : playerPermanents) {
      if (permanent != null) {
        toExile.add(permanent);
      }
    }

    return toExile;
  }

  @Override
  public boolean apply(Game game, Ability source) {
    Player player = game.getPlayer(source.getControllerId());
    MageObject sourceObject = source.getSourceObject(game);

    if (player == null) {
      return false;
    }

    Set<Card> toExile = getNontokenCreatureCards(game, player);

    UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
    player.moveCardsToExile(toExile, source, game, true, exileId, sourceObject.getIdName());

    Integer result = player.rollDice(outcome, source, game, 20);

    Cards cardsToReturn = ExileUtil.returnCardsFromExile(toExile, game);

    Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
    effect.setTargetPointer(new FixedTargets(cardsToReturn, game));

    if (result < 10) {
      AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
          effect);
      game.addDelayedTriggeredAbility(delayedAbility, source);
    } else {
      effect.apply(game, source);

      toExile = getNontokenCreatureCards(game, player);
      exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
      player.moveCardsToExile(toExile, source, game, true, exileId, sourceObject.getIdName());

      cardsToReturn = ExileUtil.returnCardsFromExile(toExile, game);
      effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
      effect.setTargetPointer(new FixedTargets(cardsToReturn, game));

      AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
          effect);
      game.addDelayedTriggeredAbility(delayedAbility, source);
    }

    return true;
  }
}
