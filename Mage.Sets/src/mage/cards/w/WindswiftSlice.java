package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElfWarriorToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static mage.filter.StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL;

/**
 * @author bwsinger
 */
public final class WindswiftSlice extends CardImpl {

    public WindswiftSlice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");


        // Target creature you control deals damage equal to its power to target creature you don't control. Create a number of 1/1 green Elf Warrior creature tokens equal to the amount of excess damage dealt this way.
        this.getSpellAbility().addEffect(new WindswiftSliceEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(FILTER_CREATURE_YOU_DONT_CONTROL));
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
        this.setTargetPointer(new EachTargetPointer());
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
        List<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.size() < 2) {
            return false;
        }
        Permanent permanent = permanents.get(0);
        int power = permanent.getPower().getValue();
        if (power < 1) {
            return false;
        }
        Permanent creature = permanents.get(1);
        int excess = creature.damageWithExcess(power, permanent.getId(), source, game);
        if (excess > 0) {
            new ElfWarriorToken().putOntoBattlefield(excess, game, source);
        }
        return true;
    }
}
