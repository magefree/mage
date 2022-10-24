
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBlockAttackActivateAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class PrisonTerm extends CardImpl {
    
    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature");
    
    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public PrisonTerm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{W}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can't attack or block, and its activated abilities can't be activated.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockAttackActivateAttachedEffect()));

        // Whenever a creature enters the battlefield under an opponent's control, you may attach Prison Term to that creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new PrisonTermEffect(), filter, true, SetTargetPointer.PERMANENT, "Whenever a creature enters the battlefield under an opponent's control, you may attach Prison Term to that creature."));
    }

    private PrisonTerm(final PrisonTerm card) {
        super(card);
    }

    @Override
    public PrisonTerm copy() {
        return new PrisonTerm(this);
    }
}

class PrisonTermEffect extends OneShotEffect {

    public PrisonTermEffect() {
        super(Outcome.Detriment);
    }

    public PrisonTermEffect(final PrisonTermEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Permanent opponentCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent prisonTerm = game.getPermanent(source.getSourceId());
        if (you != null && opponentCreature != null && prisonTerm != null) {
            Permanent oldCreature = game.getPermanent(prisonTerm.getAttachedTo());
            if (oldCreature == null) {
                return false;
            }
            if (oldCreature.removeAttachment(prisonTerm.getId(), source, game)) {
                return opponentCreature.addAttachment(prisonTerm.getId(), source, game);
            }
        }
        return false;
    }

    @Override
    public PrisonTermEffect copy() {
        return new PrisonTermEffect(this);
    }
}
