package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.LinkedList;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GlamerSpinners extends CardImpl {

    public GlamerSpinners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W/U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Glamer Spinners enters the battlefield, attach all Auras enchanting target permanent to another permanent with the same controller.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GlamerSpinnersEffect(), false);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

    }

    private GlamerSpinners(final GlamerSpinners card) {
        super(card);
    }

    @Override
    public GlamerSpinners copy() {
        return new GlamerSpinners(this);
    }
}

class GlamerSpinnersEffect extends OneShotEffect {

    public GlamerSpinnersEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "attach all Auras enchanting target permanent to another permanent with the same controller";
    }

    public GlamerSpinnersEffect(final GlamerSpinnersEffect effect) {
        super(effect);
    }

    @Override
    public GlamerSpinnersEffect copy() {
        return new GlamerSpinnersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        /*
         5/1/2008 	When Glamer Spinners enters the battlefield, you target only one permanent: the one that will be losing its Auras. You don't choose the permanent that will be receiving the Auras until the ability resolves.
         5/1/2008 	You may target a permanent that has no Auras enchanting it.
         5/1/2008 	When the ability resolves, you choose the permanent that will be receiving the Auras. It can't be the targeted permanent, it must have the same controller as the targeted permanent, and it must be able to be enchanted by all the Auras attached to the targeted permanent. If you can't choose a permanent that meets all those criteria, the Auras won't move.
         */

        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        Permanent glamerSpinners = game.getPermanent(source.getSourceId());
        if (targetPermanent != null
                && controller != null
                && glamerSpinners != null) {
            boolean passed = true;
            FilterPermanent filterChoice = new FilterPermanent("a different permanent with the same controller as the target to attach the enchantments to");
            filterChoice.add(new ControllerIdPredicate(targetPermanent.getControllerId()));
            filterChoice.add(Predicates.not(new PermanentIdPredicate(targetPermanent.getId())));

            Target chosenPermanentToAttachAuras = new TargetPermanent(filterChoice);
            chosenPermanentToAttachAuras.setNotTarget(true);

            LinkedList<UUID> auras = new LinkedList<>();
            auras.addAll(targetPermanent.getAttachments());
            if (source.getSourceObjectZoneChangeCounter() == glamerSpinners.getZoneChangeCounter(game) // not blinked
                    && chosenPermanentToAttachAuras.canChoose(source.getControllerId(), source, game)
                    && controller.choose(Outcome.Neutral, chosenPermanentToAttachAuras, source, game)) {
                Permanent permanentToAttachAuras = game.getPermanent(chosenPermanentToAttachAuras.getFirstTarget());
                if (permanentToAttachAuras != null) {
                    for (UUID auraId : auras) {
                        Permanent aura = game.getPermanent(auraId);
                        if (aura != null
                                && passed) {
                            // Check the target filter
                            Target target = aura.getSpellAbility().getTargets().get(0);
                            if (target instanceof TargetPermanent) {
                                if (!target.getFilter().match(permanentToAttachAuras, game)) {
                                    passed = false;
                                }
                            }
                            // Check for protection
                            MageObject auraObject = game.getObject(auraId);
                            if (auraObject != null) {
                                if (permanentToAttachAuras.cantBeAttachedBy(auraObject, source, game, true)) {
                                    passed = false;
                                }
                            }
                        }
                    }
                    if (passed) {
                        LinkedList<UUID> aurasToAttach = new LinkedList<>();
                        aurasToAttach.addAll(auras);

                        for (UUID auraId : aurasToAttach) {
                            Permanent auraToAttachToPermanent = game.getPermanent(auraId);
                            targetPermanent.removeAttachment(auraToAttachToPermanent.getId(), source, game);
                            permanentToAttachAuras.addAttachment(auraToAttachToPermanent.getId(), source, game);
                        }
                        return true;
                    }
                    game.informPlayers("Glamer Spinners" + ": No enchantments were moved from the target permanent.");
                }
            }
            return true;
        }
        return false;
    }
}
