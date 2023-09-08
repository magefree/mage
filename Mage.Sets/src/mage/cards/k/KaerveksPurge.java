package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author sinsedrix
 */
public final class KaerveksPurge extends CardImpl {

    public KaerveksPurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{R}");

        // Destroy target creature with converted mana cost X. If that creature dies this way, Kaervek's Purge deals damage equal to the creature's power to the creature's controller.
        this.getSpellAbility().addEffect(new KaerveksPurgeEffect());
        this.getSpellAbility().setTargetAdjuster(KaerveksPurgeAdjuster.instance);
    }

    private KaerveksPurge(final KaerveksPurge card) {
        super(card);
    }

    @Override
    public KaerveksPurge copy() {
        return new KaerveksPurge(this);
    }
}

enum KaerveksPurgeAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value " + xValue);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.addTarget(new TargetCreaturePermanent(filter));
    }
}

class KaerveksPurgeEffect extends OneShotEffect {

    public KaerveksPurgeEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature with mana value X. " +
                "If that creature dies this way, " +
                "{this} deals damage equal to the creature's power" +
                " to the creature's controller";
    }

    private KaerveksPurgeEffect(final KaerveksPurgeEffect effect) {
        super(effect);
    }

    @Override
    public KaerveksPurgeEffect copy() {
        return new KaerveksPurgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Destroy target creature with converted mana cost X.
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null && targetCreature.destroy(source, game, false)) {
            game.getState().processAction(game);
            if (targetCreature.getZoneChangeCounter(game) + 1 == game.getState().getZoneChangeCounter(targetCreature.getId())
                    && game.getState().getZone(targetCreature.getId()) != Zone.GRAVEYARD) {
                // A replacement effect has moved the card to another zone as graveyard
                return true;
            }
            // If that creature dies this way, Kaervek's Purge deals damage equal to the creature's power to the creature's controller
            Player creatureController = game.getPlayer(targetCreature.getControllerId());
            int power = targetCreature.getPower().getValue();
            if (creatureController != null) {
                creatureController.damage(power, source.getSourceId(), source, game);
            }
        }
        return true;
    }

}
