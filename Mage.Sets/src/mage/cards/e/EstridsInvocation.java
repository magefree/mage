package mage.cards.e;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.ApplyToPermanent;

/**
 *
 * @author TheElk801
 */
public final class EstridsInvocation extends CardImpl {

    public EstridsInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // You may have Estrid's Invocation enter the battlefield as a copy of any enchantment you control, except it gains "At the beginning of your upkeep, you may exile this enchantment. If you do, return it to the battlefield under its owner's control."
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(
                StaticFilters.FILTER_ENCHANTMENT_PERMANENT,
                new EstridsInvocationApplier()
        ).setText("as a copy of any enchantment you control, except it gains "
                + "\"At the beginning of your upkeep, "
                + "you may exile this enchantment. "
                + "If you do, return it to the battlefield "
                + "under its owner's control.\""), true
        ));
    }

    public EstridsInvocation(final EstridsInvocation card) {
        super(card);
    }

    @Override
    public EstridsInvocation copy() {
        return new EstridsInvocation(this);
    }
}

class EstridsInvocationApplier extends ApplyToPermanent {

    @Override
    public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
        // At the beginning of your upkeep, you may exile this enchantment. If you do, return it to the battlefield under its owner's control.
        permanent.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new EstridsInvocationEffect(), TargetController.YOU, true
        ), source.getSourceId(), game);
        return true;
    }

    @Override
    public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
        // At the beginning of your upkeep, you may exile this enchantment. If you do, return it to the battlefield under its owner's control.
        mageObject.getAbilities().add(new BeginningOfUpkeepTriggeredAbility(
                new EstridsInvocationEffect(), TargetController.YOU, true
        ));
        return true;
    }

}

class EstridsInvocationEffect extends OneShotEffect {

    public EstridsInvocationEffect() {
        super(Outcome.Neutral);
        this.staticText = "you may exile this enchantment. "
                + "If you do, return it to the battlefield under its owner's control";
    }

    public EstridsInvocationEffect(final EstridsInvocationEffect effect) {
        super(effect);
    }

    @Override
    public EstridsInvocationEffect copy() {
        return new EstridsInvocationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            if (permanent.moveToExile(source.getSourceId(), "Estrid's Invocation", source.getSourceId(), game)) {
                Card card = game.getExile().getCard(source.getSourceId(), game);
                if (card != null) {
                    return card.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
                }
            }
        }
        return false;
    }
}
