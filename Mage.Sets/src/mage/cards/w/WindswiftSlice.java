package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElfWarriorToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author bwsinger
 */
public final class WindswiftSlice extends CardImpl {

    public WindswiftSlice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");
        

        // Target creature you control deals damage equal to its power to target creature you don't control. Create a number of 1/1 green Elf Warrior creature tokens equal to the amount of excess damage dealt this way.
        this.getSpellAbility().addEffect(new WindswiftSliceEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private WindswiftSlice(final WindswiftSlice card) {
        super(card);
    }

    @Override
    public WindswiftSlice copy() {
        return new WindswiftSlice(this);
    }
}

class WindswiftSliceEffect extends OneShotEffect {

    WindswiftSliceEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature you control deals damage equal to its power to target " +
                "creature you don't control. Create a number of 1/1 green Elf Warrior creature " +
                "tokens equal to the amount of excess damage dealt this way.";
    }

    private WindswiftSliceEffect(final WindswiftSliceEffect effect) {
        super(effect);
    }

    @Override
    public WindswiftSliceEffect copy() {
        return new WindswiftSliceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent myPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent anotherPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());

        if (myPermanent == null || anotherPermanent == null) {
            return false;
        }

        int power = myPermanent.getPower().getValue();
        if (power < 1) {
            return false;
        }

        int lethal = anotherPermanent.getLethalDamage(myPermanent.getId(), game);
        lethal = Math.min(lethal, power);

        anotherPermanent.damage(lethal, myPermanent.getId(), source, game);

        if (lethal < power) {
            new ElfWarriorToken().putOntoBattlefield(power - lethal, game, source, source.getControllerId());
        }

        return true;
    }
}
