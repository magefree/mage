package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SharesCreatureTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ReinsOfTheVinesteed extends CardImpl {

    public ReinsOfTheVinesteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 2, Duration.WhileOnBattlefield)));

        // When enchanted creature dies, you may return Reins of the Vinesteed from your graveyard to the battlefield attached to a creature that shares a creature type with that creature.
        this.addAbility(new DiesAttachedTriggeredAbility(new ReinsOfTheVinesteedEffect(), "enchanted creature", true));

    }

    private ReinsOfTheVinesteed(final ReinsOfTheVinesteed card) {
        super(card);
    }

    @Override
    public ReinsOfTheVinesteed copy() {
        return new ReinsOfTheVinesteed(this);
    }
}

class ReinsOfTheVinesteedEffect extends OneShotEffect {

    public ReinsOfTheVinesteedEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "you may return {this} from your graveyard to the battlefield attached to a creature that shares a creature type with that creature";
    }

    public ReinsOfTheVinesteedEffect(final ReinsOfTheVinesteedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card aura = game.getCard(source.getSourceId());
        if (aura != null
                && game.getState().getZone(aura.getId()) == Zone.GRAVEYARD) {
            Player controller = game.getPlayer(source.getControllerId());
            Permanent lastStateAura = (Permanent) game.getLastKnownInformation(aura.getId(), Zone.BATTLEFIELD);
            Permanent lastStateCreature = game.getPermanentOrLKIBattlefield(lastStateAura.getAttachedTo());
            if (lastStateCreature == null) {
                return false;
            }
            FilterPermanent FILTER = new FilterCreaturePermanent(
                    "creature that shares a creature type with " + lastStateCreature.getName()
            );
            FILTER.add(new SharesCreatureTypePredicate(lastStateCreature));
            TargetPermanent target = new TargetPermanent(FILTER);
            target.setNotTarget(true);
            if (controller != null
                    && controller.choose(Outcome.PutCardInPlay, target, source, game)) {
                Permanent targetPermanent = game.getPermanent(target.getFirstTarget());
                if (!targetPermanent.cantBeAttachedBy(aura, source, game, false)) {
                    game.getState().setValue("attachTo:" + aura.getId(), targetPermanent);
                    controller.moveCards(aura, Zone.BATTLEFIELD, source, game);
                    return targetPermanent.addAttachment(aura.getId(), source, game);
                }
            }
        }
        return false;
    }

    @Override
    public ReinsOfTheVinesteedEffect copy() {
        return new ReinsOfTheVinesteedEffect(this);
    }
}
