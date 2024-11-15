
package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SaprolingToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LastStand extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.SWAMP));
    private static final Hint hint = new ValueHint("Swamps you control", xValue);
    private static final DynamicValue xValue2 = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.MOUNTAIN));
    private static final Hint hint2 = new ValueHint("Mountains you control", xValue2);
    private static final DynamicValue xValue3 = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.FOREST));
    private static final Hint hint3 = new ValueHint("Forests you control", xValue3);
    private static final DynamicValue xValue4 = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.PLAINS));
    private static final Hint hint4 = new ValueHint("Plains you control", xValue4);
    private static final DynamicValue xValue5 = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.ISLAND));
    private static final Hint hint5 = new ValueHint("Islands you control", xValue5);


    public LastStand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{U}{B}{R}{G}");

        // Target opponent loses 2 life for each Swamp you control. Last Stand deals damage to target creature equal to the number of Mountains you control. Create a 1/1 green Saproling creature token for each Forest you control. You gain 2 life for each Plains you control. Draw a card for each Island you control, then discard that many cards.
        this.getSpellAbility().addEffect(new LastStandEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);
        this.getSpellAbility().addHint(hint2);
        this.getSpellAbility().addHint(hint3);
        this.getSpellAbility().addHint(hint4);
        this.getSpellAbility().addHint(hint5);
    }

    private LastStand(final LastStand card) {
        super(card);
    }

    @Override
    public LastStand copy() {
        return new LastStand(this);
    }
}

class LastStandEffect extends OneShotEffect {

    private static final FilterControlledLandPermanent filterSwamp = new FilterControlledLandPermanent();
    private static final FilterControlledLandPermanent filterMountain = new FilterControlledLandPermanent();
    private static final FilterControlledLandPermanent filterPlains = new FilterControlledLandPermanent();
    private static final FilterControlledLandPermanent filterForest = new FilterControlledLandPermanent();
    private static final FilterControlledLandPermanent filterIsland = new FilterControlledLandPermanent();

    static {
        filterSwamp.add(SubType.SWAMP.getPredicate());
        filterMountain.add(SubType.MOUNTAIN.getPredicate());
        filterPlains.add(SubType.PLAINS.getPredicate());
        filterForest.add(SubType.FOREST.getPredicate());
        filterIsland.add(SubType.ISLAND.getPredicate());
    }

    public LastStandEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent loses 2 life for each Swamp you control. Last Stand deals damage to target creature equal to the number of Mountains you control. Create a 1/1 green Saproling creature token for each Forest you control. You gain 2 life for each Plains you control. Draw a card for each Island you control, then discard that many cards";
    }

    private LastStandEffect(final LastStandEffect effect) {
        super(effect);
    }

    @Override
    public LastStandEffect copy() {
        return new LastStandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Target opponent loses 2 life for each Swamp you control
            Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (opponent != null) {
                int swamps = game.getBattlefield().count(filterSwamp, source.getControllerId(), source, game);
                opponent.loseLife(swamps * 2, game, source, false);
            }
            // Last Stand deals damage equal to the number of Mountains you control to target creature.
            Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (creature != null) {
                int mountains = game.getBattlefield().count(filterMountain, source.getControllerId(), source, game);
                if (mountains > 0) {
                    creature.damage(mountains, source.getSourceId(), source, game, false, true);
                }
            }
            // Create a 1/1 green Saproling creature token for each Forest you control.
            int forests = game.getBattlefield().count(filterForest, source.getControllerId(), source, game);
            if (forests > 0) {
                new CreateTokenEffect(new SaprolingToken(), forests).apply(game, source);
            }
            // You gain 2 life for each Plains you control.
            int plains = game.getBattlefield().count(filterPlains, source.getControllerId(), source, game);
            controller.gainLife(plains * 2, game, source);
            // Draw a card for each Island you control, then discard that many cards
            int islands = game.getBattlefield().count(filterIsland, source.getControllerId(), source, game);
            if (islands > 0) {
                controller.drawCards(islands, source, game);
                controller.discard(islands, false, false, source, game);
            }

        }
        return false;
    }
}
