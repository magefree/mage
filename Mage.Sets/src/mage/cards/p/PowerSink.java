package mage.cards.p;

import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.ManaUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author Quercitron
 */
public final class PowerSink extends CardImpl {

    public PowerSink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Counter target spell unless its controller pays {X}. If that player doesn't, they tap all lands with mana abilities they control and lose all unspent mana.
        this.getSpellAbility().addEffect(new PowerSinkCounterUnlessPaysEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private PowerSink(final PowerSink card) {
        super(card);
    }

    @Override
    public PowerSink copy() {
        return new PowerSink(this);
    }
}

class PowerSinkCounterUnlessPaysEffect extends OneShotEffect {

    public PowerSinkCounterUnlessPaysEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell unless its controller pays {X}. If that player doesn't, they tap all lands with mana abilities they control and lose all unspent mana";
    }

    public PowerSinkCounterUnlessPaysEffect(final PowerSinkCounterUnlessPaysEffect effect) {
        super(effect);
    }

    @Override
    public PowerSinkCounterUnlessPaysEffect copy() {
        return new PowerSinkCounterUnlessPaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            Player controller = game.getPlayer(source.getControllerId());
            MageObject sourceObject = game.getObject(source);
            if (player != null && controller != null && sourceObject != null) {
                int amount = source.getManaCostsToPay().getX();
                if (amount > 0) {
                    Cost cost = ManaUtil.createManaCost(amount, true);
                    if (player.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + " to prevent?", source, game)) {
                        if (cost.pay(source, game, source, player.getId(), false)) {
                            game.informPlayers(sourceObject.getName() + ": additional cost was paid");
                            return true;
                        }
                    }
                    game.informPlayers(sourceObject.getName() + ": additional cost wasn't paid - countering " + spell.getName());

                    // Counter target spell unless its controller pays {X}
                    game.getStack().counter(source.getFirstTarget(), source, game);

                    // that player taps all lands with mana abilities they control...
                    List<Permanent> lands = game.getBattlefield().getAllActivePermanents(new FilterLandPermanent(), player.getId(), game);
                    for (Permanent land : lands) {
                        Abilities<Ability> landAbilities = land.getAbilities();
                        for (Ability ability : landAbilities) {
                            if (ability instanceof ActivatedManaAbilityImpl) {
                                land.tap(source, game);
                                break;
                            }
                        }
                    }

                    // ...and empties their mana pool
                    player.getManaPool().emptyPool(game);
                }
                return true;
            }
        }
        return false;
    }

}
