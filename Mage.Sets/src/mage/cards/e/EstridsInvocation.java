package mage.cards.e;

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
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EstridsInvocation extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledEnchantmentPermanent();

    public EstridsInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // You may have Estrid's Invocation enter the battlefield as a copy of any enchantment you control, except it gains "At the beginning of your upkeep, you may exile this enchantment. If you do, return it to the battlefield under its owner's control."
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(
                filter, new EstridsInvocationCopyApplier()
        ).setText("as a copy of an enchantment you control, except it gains "
                + "\"At the beginning of your upkeep, "
                + "you may exile this enchantment. "
                + "If you do, return it to the battlefield "
                + "under its owner's control.\""), true
        ));
    }

    private EstridsInvocation(final EstridsInvocation card) {
        super(card);
    }

    @Override
    public EstridsInvocation copy() {
        return new EstridsInvocation(this);
    }
}

class EstridsInvocationCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        // At the beginning of your upkeep, you may exile this enchantment. If you do, return it to the battlefield under its owner's control.
        blueprint.getAbilities().add(new BeginningOfUpkeepTriggeredAbility(
                new EstridsInvocationEffect(), TargetController.YOU, true
        ));
        return true;
    }
}

class EstridsInvocationEffect extends OneShotEffect {

    EstridsInvocationEffect() {
        super(Outcome.Neutral);
        this.staticText = "exile this enchantment. If you do, return it to the battlefield under its owner's control";
    }

    private EstridsInvocationEffect(final EstridsInvocationEffect effect) {
        super(effect);
    }

    @Override
    public EstridsInvocationEffect copy() {
        return new EstridsInvocationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || player == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        player.moveCards(permanent, Zone.EXILED, source, game);
        player.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
        return true;
    }
}
