package mage.cards.p;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class Paroxysm extends CardImpl {

    public Paroxysm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of the upkeep of enchanted creature's controller, that player reveals the top card of their library.
        // If that card is a land card, destroy that creature. Otherwise, it gets +3/+3 until end of turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD,
                new ParoxysmEffect(),
                TargetController.CONTROLLER_ATTACHED_TO,
                false, false, "At the beginning of the upkeep of enchanted creature's controller, "));
    }

    private Paroxysm(final Paroxysm card) {
        super(card);
    }

    @Override
    public Paroxysm copy() {
        return new Paroxysm(this);
    }
}

class ParoxysmEffect extends OneShotEffect {

    ParoxysmEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "that player reveals the top card of their library. \n"
                + "If that card is a land card, destroy that creature. \n"
                + "Otherwise, it gets +3/+3 until end of turn.";
    }

    ParoxysmEffect(final ParoxysmEffect effect) {
        super(effect);
    }

    @Override
    public ParoxysmEffect copy() {
        return new ParoxysmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = game.getPermanent(source.getSourceId());
        if (aura != null) {
            Permanent creatureAttachedTo = game.getPermanent(aura.getAttachedTo());
            if (creatureAttachedTo != null) {
                Player controllerOfCreature = game.getPlayer(creatureAttachedTo.getControllerId());
                if (controllerOfCreature != null) {
                    Card revealCardFromTop = controllerOfCreature.getLibrary().getFromTop(game);
                    if (revealCardFromTop != null) {
                        Cards cards = new CardsImpl(revealCardFromTop);
                        controllerOfCreature.revealCards(source, cards, game);
                        if (revealCardFromTop.isLand(game)) {
                            creatureAttachedTo.destroy(source, game, false);
                        } else {
                            ContinuousEffect effect = new BoostTargetEffect(3, 3, Duration.EndOfTurn);
                            effect.setTargetPointer(new FixedTarget(creatureAttachedTo.getId(), game));
                            game.addEffect(effect, source);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
