package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author fenhl
 */
public final class PacksDisdain extends CardImpl {

    public PacksDisdain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Choose a creature type. Target creature gets -1/-1 until end of turn for each permanent of the chosen type you control.
        this.getSpellAbility().addEffect(new PacksDisdainEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PacksDisdain(final PacksDisdain card) {
        super(card);
    }

    @Override
    public PacksDisdain copy() {
        return new PacksDisdain(this);
    }
}

class PacksDisdainEffect extends OneShotEffect {

    PacksDisdainEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "Choose a creature type. Target creature gets -1/-1 until end of turn for each permanent of the chosen type you control";
    }

    PacksDisdainEffect(final PacksDisdainEffect effect) {
        super(effect);
    }

    @Override
    public PacksDisdainEffect copy() {
        return new PacksDisdainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Choice typeChoice = new ChoiceCreatureType(game.getObject(source));
        if (player != null
                && player.choose(Outcome.UnboostCreature, typeChoice, game)) {
            FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
            filter.add(SubType.byDescription(typeChoice.getChoice()).getPredicate());
            DynamicValue negativePermanentsCount = new PermanentsOnBattlefieldCount(filter, -1);
            ContinuousEffect effect = new BoostTargetEffect(negativePermanentsCount, negativePermanentsCount, Duration.EndOfTurn, true);
            effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
