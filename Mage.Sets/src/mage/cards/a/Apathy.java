package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Apathy extends CardImpl {

    public Apathy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepEnchantedEffect()));

        // At the beginning of the upkeep of enchanted creature’s controller, that player may discard a card at random. If the player does, untap that creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ApathyEffect(), TargetController.CONTROLLER_ATTACHED_TO, false
        ));
    }

    private Apathy(final Apathy card) {
        super(card);
    }

    @Override
    public Apathy copy() {
        return new Apathy(this);
    }
}

class ApathyEffect extends OneShotEffect {

    ApathyEffect() {
        super(Outcome.Benefit);
        staticText = "that player may discard a card at random. If the player does, untap that creature";
    }

    private ApathyEffect(final ApathyEffect effect) {
        super(effect);
    }

    @Override
    public ApathyEffect copy() {
        return new ApathyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player == null) {
            return false;
        }
        if (!player.chooseUse(outcome, "Discard a card at random to untap enchanted creature?", source, game)
                || player.discardOne(true, false, source, game) == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        permanent = game.getPermanent(permanent.getAttachedTo());
        return permanent != null && permanent.untap(game);
    }
}
