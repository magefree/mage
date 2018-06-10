
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author spjspj
 */
public final class ArmoryAutomaton extends CardImpl {

    public ArmoryAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Armory Automaton enters the battlefield or attacks, attach any number of target Equipment to it.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ArmoryAutomatonEffect()));
    }

    public ArmoryAutomaton(final ArmoryAutomaton card) {
        super(card);
    }

    @Override
    public ArmoryAutomaton copy() {
        return new ArmoryAutomaton(this);
    }
}

class ArmoryAutomatonEffect extends OneShotEffect {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("Equipment");

    static {
        filter.add(new SubtypePredicate(SubType.EQUIPMENT));
    }

    public ArmoryAutomatonEffect() {
        super(Outcome.Benefit);
        this.staticText = "attach any number of target Equipment to it";
    }

    public ArmoryAutomatonEffect(final ArmoryAutomatonEffect effect) {
        super(effect);
    }

    @Override
    public ArmoryAutomatonEffect copy() {
        return new ArmoryAutomatonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            int countBattlefield = game.getBattlefield().getAllActivePermanents(filter, game).size() - sourcePermanent.getAttachments().size();
            while (player.canRespond() && countBattlefield > 0 && player.chooseUse(Outcome.Benefit, "Attach a target Equipment?", source, game)) {
                Target targetEquipment = new TargetPermanent(filter);
                if (player.choose(Outcome.Benefit, targetEquipment, source.getSourceId(), game)) {
                    Permanent aura = game.getPermanent(targetEquipment.getFirstTarget());
                    if (aura != null) {
                        Permanent attachedTo = game.getPermanent(aura.getAttachedTo());
                        if (attachedTo != null) {
                            attachedTo.removeAttachment(aura.getId(), game);
                        }
                        sourcePermanent.addAttachment(aura.getId(), game);
                    }
                }
                countBattlefield = game.getBattlefield().getAllActivePermanents(filter, game).size() - sourcePermanent.getAttachments().size();
            }
            return true;
        }
        return false;
    }
}
