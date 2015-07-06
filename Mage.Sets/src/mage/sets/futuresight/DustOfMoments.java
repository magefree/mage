/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.futuresight;

import java.util.List;
import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.permanent.CardCounterPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Gal Lerman

 */
public class DustOfMoments extends CardImpl {

    public DustOfMoments(UUID ownerId) {
        super(ownerId, 5, "Dust of Moments", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{W}");
        this.expansionSetCode = "FUT";

        // Choose one - Remove two time counters from each permanent and each suspended card
        this.getSpellAbility().addEffect(new RemoveCountersEffect());

        // Or put two time counters on each permanent with a time counter on it and each suspended card
        Mode mode = new Mode();
        mode.getEffects().add(new AddCountersEffect());
        this.getSpellAbility().addMode(mode);
    }

    public DustOfMoments(final DustOfMoments card) {
        super(card);
    }

    @Override
    public DustOfMoments copy() {
        return new DustOfMoments(this);
    }


  //TODO: PermanentImpl.getCounters() and CardImpl.getCounters(game) don't return the same value for the same Card
  //TODO: This means I can't use a Card generic for Permanents and Exiled cards and use Card.getCounters(game)
  //TODO: This is the reason i've copy pasted some logic in DustOfMomentsEffect
  //TODO: After this issue is fixed/explained i'll refactor the code
  public abstract static class DustOfMomentsEffect extends OneShotEffect {

      private final Counter counter;
      private final Filter<Card> permFilter;
      private final Filter<Card> exiledFilter;

    public DustOfMomentsEffect() {
          super(Outcome.Benefit);
          this.counter = new Counter(CounterType.TIME.getName(), 2);
          this.permFilter = new FilterCard("permanent and each suspended card");
          permFilter.add(new CounterPredicate(CounterType.TIME));

          this.exiledFilter = new FilterCard("permanent and each suspended card");
          exiledFilter.add(new CardCounterPredicate(CounterType.TIME));
          setText();
      }

      public DustOfMomentsEffect(final DustOfMomentsEffect effect) {
          super(effect);
          this.counter = effect.counter.copy();
          this.permFilter = effect.permFilter.copy();
          this.exiledFilter = effect.exiledFilter.copy();
      }

      @Override
      public boolean apply(Game game, Ability source) {
          Player controller = game.getPlayer(source.getControllerId());
          MageObject sourceObject = game.getObject(source.getSourceId());
          if (controller != null && sourceObject != null) {
              updatePermanents(game, controller, sourceObject);
              updateSuspended(game, controller, sourceObject);
              return true;
          }
          return false;
      }

    private void updateSuspended(final Game game, final Player controller, final MageObject sourceObject) {
      final List<Card> exiledCards = game.getExile().getAllCards(game);
      execute(game, controller, sourceObject, exiledCards);
    }

    private void updatePermanents(final Game game, final Player controller, final MageObject sourceObject) {
      List<Permanent> permanents = game.getBattlefield().getAllActivePermanents();
      executeP(game, controller, sourceObject, permanents);
    }

    private void executeP(final Game game, final Player controller, final MageObject sourceObject, final List<Permanent> cards) {
      if (cards == null || cards.isEmpty()) {
        return;
      }
      for (Permanent card : cards) {
          if (permFilter.match(card, game)) {
              final String counterName = counter.getName();
              if (shouldRemoveCounters()) {
                  final Counter existingCounterOfSameType = card.getCounters().get(counterName);
                  final int countersToRemove = Math.min(existingCounterOfSameType.getCount(), counter.getCount());
                  final Counter modifiedCounter = new Counter(counterName, countersToRemove);
                  card.removeCounters(modifiedCounter, game);
              } else {
                  card.addCounters(counter, game);
              }
              if (!game.isSimulation())
                  game.informPlayers(new StringBuilder(sourceObject.getName()).append(": ")
                          .append(controller.getLogName()).append(getActionStr()).append("s")
                          .append(counter.getCount()).append(" ").append(counterName.toLowerCase())
                          .append(" counter on ").append(card.getName()).toString());
          }
      }
    }

    private void execute(final Game game, final Player controller, final MageObject sourceObject, final List<Card> cards) {
      if (cards == null || cards.isEmpty()) {
        return;
      }
      for (Card card : cards) {
        if (exiledFilter.match(card, game)) {
          final String counterName = counter.getName();
          if (shouldRemoveCounters()) {
            final Counter existingCounterOfSameType = card.getCounters(game).get(counterName);
            final int countersToRemove = Math.min(existingCounterOfSameType.getCount(), counter.getCount());
            final Counter modifiedCounter = new Counter(counterName, countersToRemove);
            card.removeCounters(modifiedCounter, game);
          } else {
            card.addCounters(counter, game);
          }
          if (!game.isSimulation())
            game.informPlayers(new StringBuilder(sourceObject.getName()).append(": ")
                    .append(controller.getLogName()).append(getActionStr()).append("s ")
                    .append(counter.getCount()).append(" ").append(counterName.toLowerCase())
                    .append(" counter on ").append(card.getName()).toString());
        }
      }
    }

    protected abstract boolean shouldRemoveCounters();

    protected abstract String getActionStr();

    private void setText() {
          StringBuilder sb = new StringBuilder();
          sb.append(getActionStr());
          if (counter.getCount() > 1) {
              sb.append(Integer.toString(counter.getCount())).append(" ").append(counter.getName().toLowerCase()).append(" counters on each ");
          } else {
              sb.append("a ").append(counter.getName().toLowerCase()).append(" counter on each ");
          }
          sb.append(permFilter.getMessage());
          staticText = sb.toString();
      }
  }

  public static class AddCountersEffect extends DustOfMomentsEffect {

    public AddCountersEffect() {
      super();
    }

    public AddCountersEffect(final DustOfMomentsEffect effect) {
      super(effect);
    }

    @Override
    protected boolean shouldRemoveCounters() {
      return false;
    }

    @Override
    protected String getActionStr() {
      return "add";
    }

    @Override
    public Effect copy() {
      return new AddCountersEffect(this);
    }
  }

  public static class RemoveCountersEffect extends DustOfMomentsEffect {

    public RemoveCountersEffect() {
      super();
    }

    public RemoveCountersEffect(final DustOfMomentsEffect effect) {
      super(effect);
    }

    @Override
    protected boolean shouldRemoveCounters() {
      return true;
    }

    @Override
    protected String getActionStr() {
      return "remove";
    }

    @Override
    public Effect copy() {
      return new RemoveCountersEffect(this);
    }
  }
}
