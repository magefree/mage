package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
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
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Kudzu extends CardImpl {

    public Kudzu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When enchanted land becomes tapped, destroy it. That land's controller attaches Kudzu to a land of their choice.
        this.addAbility(new BecomesTappedAttachedTriggeredAbility(new KudzuEffect(), "enchanted land"));

    }

    private Kudzu(final Kudzu card) {
        super(card);
    }

    @Override
    public Kudzu copy() {
        return new Kudzu(this);
    }
}

class KudzuEffect extends OneShotEffect {

    public KudzuEffect() {
        super(Outcome.Detriment);
        staticText = "destroy it. That land's controller attaches {this} to a land of their choice";
    }

    public KudzuEffect(final KudzuEffect effect) {
        super(effect);
    }

    @Override
    public KudzuEffect copy() {
        return new KudzuEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent kudzu = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (kudzu != null) {
            Permanent enchantedLand = game.getPermanentOrLKIBattlefield(kudzu.getAttachedTo());
            Player controller = game.getPlayer(source.getControllerId());
            if (enchantedLand != null
                    && controller != null) {
                Player landsController = game.getPlayer(enchantedLand.getControllerId());
                if (game.getState().getZone(enchantedLand.getId()) == Zone.BATTLEFIELD) { // if 2 or more Kudzu's were on a land
                    enchantedLand.destroy(source, game, false);
                }
                if (!game.getBattlefield().getAllActivePermanents(CardType.LAND, game).isEmpty()) { //lands are available on the battlefield
                    Target target = new TargetLandPermanent();
                    target.setNotTarget(true); //not a target, it is chosen
                    Card kudzuCard = game.getCard(source.getSourceId());
                    if (kudzuCard != null && landsController != null) {
                        if (landsController.choose(Outcome.Detriment, target, source, game)) {
                            if (target.getFirstTarget() != null) {
                                Permanent landChosen = game.getPermanent(target.getFirstTarget());
                                if (landChosen != null) {
                                    for (Target targetTest : kudzuCard.getSpellAbility().getTargets()) {
                                        if (targetTest.getFilter().match(landChosen, game)) {
                                            landChosen.addAttachment(kudzu.getId(), source, game);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
            return true;
        }
        return false;
    }
}
