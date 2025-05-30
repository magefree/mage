package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class StrengthTestingHammer extends CardImpl {


    public StrengthTestingHammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, roll a six-sided die. That creature gets +X/+0 until end of turn, where X is the result. Then, if it has the greatest power or is tied for greatest power among creatures on the battlefield, draw a card.
        TriggeredAbility ability = new AttacksAttachedTriggeredAbility(new StrengthTestingHammerEffect());
        ability.addEffect(new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(1), StrengthTestingHammerCondition.instance).setText("Then if it has the greatest power or is tied for greatest power among creatures on the battlefield, draw a card."));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private StrengthTestingHammer(final StrengthTestingHammer card) {
        super(card);
    }

    @Override
    public StrengthTestingHammer copy() {
        return new StrengthTestingHammer(this);
    }
}

//Based on Historian's Wisdom
enum StrengthTestingHammerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent hammer = source.getSourcePermanentIfItStillExists(game);
        if (hammer == null) {
            return false;
        }
        Permanent creature = game.getPermanent(hammer.getAttachedTo());
        if (creature == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, creature.getPower().getValue()));
        return game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game).isEmpty();
    }
}

class StrengthTestingHammerEffect extends OneShotEffect {

    StrengthTestingHammerEffect() {
        super(Outcome.Benefit);
        staticText = "roll a six-sided die. That creature gets +X/+0 until end of turn, where X is the result.";
    }

    private StrengthTestingHammerEffect(final StrengthTestingHammerEffect effect) {
        super(effect);
    }

    @Override
    public StrengthTestingHammerEffect copy() {
        return new StrengthTestingHammerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int result = player.rollDice(outcome, source, game, 6);
            game.addEffect(new BoostEquippedEffect(result, 0, Duration.EndOfTurn), source);
        }
        return false;
    }
}
