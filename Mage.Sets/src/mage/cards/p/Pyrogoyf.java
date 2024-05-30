
package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessPlusOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Pyrogoyf extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.LHURGOYF, "{this} or another Lhurgoyf creature");

    private static final DynamicValue powerValue = CardTypesInGraveyardCount.ALL;

    public Pyrogoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.LHURGOYF);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Pyrogoyf's power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessPlusOneSourceEffect(powerValue)));

        // Whenever Pyrogoyf or another Lhurgoyf creature enters the battlefield under your control, that creature deals damage equal to its power to any target.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new PyrogoyfEffect(), filter, false, true
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private Pyrogoyf(final Pyrogoyf card) {
        super(card);
    }

    @Override
    public Pyrogoyf copy() {
        return new Pyrogoyf(this);
    }
}

class PyrogoyfEffect extends OneShotEffect {

    PyrogoyfEffect() {
        super(Outcome.Damage);
        staticText = "that creature deals damage equal to its power to any target";
    }

    private PyrogoyfEffect(final PyrogoyfEffect effect) {
        super(effect);
    }

    @Override
    public PyrogoyfEffect copy() {
        return new PyrogoyfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent damagingPermanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (damagingPermanent == null) {
            return false;
        }
        int damageValue = damagingPermanent.getPower().getValue();
        Permanent anotherPermanent = game.getPermanent(source.getFirstTarget());
        Player anotherPlayer = game.getPlayer(source.getFirstTarget());
        if (anotherPermanent != null) {
            anotherPermanent.damage(damageValue, damagingPermanent.getId(), source, game);
            return true;
        } else if (anotherPlayer != null) {
            anotherPlayer.damage(damageValue, damagingPermanent.getId(), source, game);
            return true;
        }
        return false;
    }
}