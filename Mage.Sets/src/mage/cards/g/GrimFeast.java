package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrimFeast extends CardImpl {

    public GrimFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{G}");

        // At the beginning of your upkeep, Grim Feast deals 1 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DamageControllerEffect(1),
                TargetController.YOU, false
        ));

        // Whenever a creature is put into an opponent's graveyard from the battlefield, you gain life equal to its toughness.
        this.addAbility(new GrimFeastTriggeredAbility());
    }

    private GrimFeast(final GrimFeast card) {
        super(card);
    }

    @Override
    public GrimFeast copy() {
        return new GrimFeast(this);
    }
}

class GrimFeastTriggeredAbility extends PutIntoGraveFromBattlefieldAllTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature");

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    public GrimFeastTriggeredAbility() {
        super(new GrimFeastEffect(), false, filter, true, false);
    }

    private GrimFeastTriggeredAbility(final GrimFeastTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public GrimFeastTriggeredAbility copy() {
        return new GrimFeastTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature is put into an opponent's graveyard from the battlefield, "
                + "you gain life equal to its toughness.";
    }
}

class GrimFeastEffect extends OneShotEffect {

    public GrimFeastEffect() {
        super(Outcome.GainLife);
    }

    private GrimFeastEffect(final GrimFeastEffect effect) {
        super(effect);
    }

    @Override
    public GrimFeastEffect copy() {
        return new GrimFeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (creature == null) {
            return false;
        }
        return new GainLifeEffect(creature.getToughness().getValue()).apply(game, source);
    }
}
