package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ThiefOfExistence extends CardImpl {

    private static final FilterPermanent filter =
            new FilterPermanent("noncreature, nonland permanent an opponent controls with mana value 4 or less");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 4));
    }

    public ThiefOfExistence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{C}{G}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When you cast this spell, exile up to one target noncreature, nonland permanent an opponent controls with mana value 4 or less. If you do, Thief of Existence gains "When this creature leaves the battlefield, target opponent draws a card."
        Ability ability = new CastSourceTriggeredAbility(new ThiefOfExistenceTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        this.addAbility(ability);
    }

    private ThiefOfExistence(final ThiefOfExistence card) {
        super(card);
    }

    @Override
    public ThiefOfExistence copy() {
        return new ThiefOfExistence(this);
    }
}

class ThiefOfExistenceTargetEffect extends OneShotEffect {

    ThiefOfExistenceTargetEffect() {
        super(Outcome.Exile);
        staticText = "exile up to one target noncreature, nonland permanent an opponent controls with mana value 4 or less. "
                + "If you do, Thief of Existence gains \"When this creature leaves the battlefield, target opponent draws a card\"";
    }

    private ThiefOfExistenceTargetEffect(final ThiefOfExistenceTargetEffect effect) {
        super(effect);
    }

    @Override
    public ThiefOfExistenceTargetEffect copy() {
        return new ThiefOfExistenceTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        if (!permanent.moveToExile(null, null, source, game)) {
            return true;
        }
        Spell sourceSpell = (Spell) source.getSourceCardIfItStillExists(game);
        if (sourceSpell == null) {
            return true;
        }
        // the target has been moved to exile. Thief gains the trigger on the stack (and will keep it on the battlefield)
        TriggeredAbility trigger = new LeavesBattlefieldTriggeredAbility(new DrawCardTargetEffect(1));
        trigger.addTarget(new TargetOpponent());
        game.addEffect(
                new GainAbilityTargetEffect(trigger, Duration.Custom, "", true)
                        .setTargetPointer(new FixedTarget(sourceSpell.getCard(), game)),
                source
        );
        return true;
    }
}