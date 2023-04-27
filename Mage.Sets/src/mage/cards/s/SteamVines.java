package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth & L_J
 */
public final class SteamVines extends CardImpl {

    public SteamVines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When enchanted land becomes tapped, destroy it and Steam Vines deals 1 damage to that land's controller.
        // That player attaches Steam Vines to a land of their choice.
        this.addAbility(new BecomesTappedAttachedTriggeredAbility(new SteamVinesEffect(), "enchanted land"));

    }

    private SteamVines(final SteamVines card) {
        super(card);
    }

    @Override
    public SteamVines copy() {
        return new SteamVines(this);
    }
}

class SteamVinesEffect extends OneShotEffect {

    public SteamVinesEffect() {
        super(Outcome.Detriment);
        staticText = "destroy it and {this} deals 1 damage to that land's controller. That player attaches {this} to a land of their choice";
    }

    public SteamVinesEffect(final SteamVinesEffect effect) {
        super(effect);
    }

    @Override
    public SteamVinesEffect copy() {
        return new SteamVinesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent steamVines = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (steamVines != null) {
            Permanent enchantedLand = game.getPermanentOrLKIBattlefield(steamVines.getAttachedTo());
            Player controller = game.getPlayer(source.getControllerId());
            if (enchantedLand != null
                    && controller != null) {
                Player landsController = game.getPlayer(enchantedLand.getControllerId());
                if (game.getState().getZone(enchantedLand.getId()) == Zone.BATTLEFIELD) { // if 2 or more Steam Vines were on a land
                    enchantedLand.destroy(source, game, false);
                    if (landsController != null) {
                        landsController.damage(1, source.getSourceId(), source, game);
                    }
                }
                if (!game.getBattlefield().getAllActivePermanents(CardType.LAND, game).isEmpty()) { //lands are available on the battlefield
                    Target target = new TargetLandPermanent();
                    target.setNotTarget(true); //not a target, it is chosen
                    Card steamVinesCard = game.getCard(source.getSourceId());
                    if (steamVinesCard != null && landsController != null) {
                        if (landsController.choose(Outcome.DestroyPermanent, target, source, game)) {
                            if (target.getFirstTarget() != null) {
                                Permanent landChosen = game.getPermanent(target.getFirstTarget());
                                if (landChosen != null) {
                                    for (Target targetTest : steamVinesCard.getSpellAbility().getTargets()) {
                                        Filter filterTest = targetTest.getFilter();
                                        if (filterTest.match(landChosen, game)) {
                                            if (game.getBattlefield().containsPermanent(landChosen.getId())) { //verify that it is still on the battlefield
                                                game.informPlayers(landsController.getLogName() + " attaches " + steamVines.getLogName() + " to " + landChosen.getLogName());
                                                Effect effect = new AttachEffect(Outcome.Neutral);
                                                effect.setTargetPointer(new FixedTarget(landChosen, game));
                                                return effect.apply(game, source);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
