package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.DontLoseByZeroOrLessLifeEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class PactWeapon extends CardImpl {

    private static final Condition condition = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);

    public PactWeapon(UUID ownerID, CardSetInfo setInfo) {
        super(ownerID, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // As long as Pact Weapon is attached to a creature, you don't lose the game for having 0 or less life.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(new DontLoseByZeroOrLessLifeEffect(Duration.WhileOnBattlefield), condition,
                "as long as {this} is attached to a creature, you don't lose the game for having 0 or less life")));

        // Whenever equipped creature attacks, draw a card and reveal it. The creature gets +X/+X until end of turn and you lose X life, where X is that card's mana value.
        this.addAbility(new AttacksAttachedTriggeredAbility(new PactWeaponEffect()));

        // Equip—Discard a card.
        this.addAbility(new EquipAbility(Outcome.AddAbility, new DiscardCardCost(), false));
    }

    private PactWeapon(final PactWeapon card) {
        super(card);
    }

    @Override
    public PactWeapon copy() {
        return new PactWeapon(this);
    }
}

class PactWeaponEffect extends OneShotEffect {

    public PactWeaponEffect() {
        super(Outcome.Benefit);
        this.staticText = "draw a card and reveal it. The creature gets +X/+X until end of turn and you lose X life, where X is that card's mana value.";
    }

    private PactWeaponEffect(final PactWeaponEffect effect) {
        super(effect);
    }

    @Override
    public PactWeaponEffect copy() {
        return new PactWeaponEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null || game.getPermanent(permanent.getAttachedTo()) == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        // Gatherer ruling (2007-02-01)
        // If the draw is replaced by another effect, none of the rest of Fa’adiyah Seer’s ability applies,
        // even if the draw is replaced by another draw (such as with Enduring Renewal).
        if (controller.drawCards(1, source, game) != 1) {
            return true;
        }
        controller.revealCards(source, new CardsImpl(card), game);
        if (card == null || card.getManaValue() == 0) {
            return true;
        }

        game.addEffect(new BoostTargetEffect(card.getManaValue(), card.getManaValue())
                .setTargetPointer(new FixedTarget(permanent.getAttachedTo(), game)), source);
        controller.loseLife(card.getManaValue(), game, source, false);

        return true;
    }
}