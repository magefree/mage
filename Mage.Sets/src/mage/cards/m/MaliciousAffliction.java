
package mage.cards.m;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MaliciousAffliction extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creatures");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public MaliciousAffliction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{B}");

        // <i>Morbid</i> &mdash; When you cast Malicious Affliction, if a creature died this turn, you may copy Malicious Affliction and may choose a new target for the copy.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new CastSourceTriggeredAbility(new CopySourceSpellEffect(), true),
                new LockedInCondition(MorbidCondition.instance),
                "<i>Morbid</i> &mdash; When you cast {this}, if a creature died this turn, you may copy {this} and may choose a new target for the copy");
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Destroy target nonblack creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public MaliciousAffliction(final MaliciousAffliction card) {
        super(card);
    }

    @Override
    public MaliciousAffliction copy() {
        return new MaliciousAffliction(this);
    }
}

class CopySourceSpellEffect extends OneShotEffect {

    static final String rule = "copy {this} and may choose a new target for the copy";

    public CopySourceSpellEffect() {
        super(Outcome.Benefit);
        staticText = rule;
    }

    public CopySourceSpellEffect(final CopySourceSpellEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Spell spell = game.getStack().getSpell(source.getSourceId());
            if (spell != null) {
                StackObject stackObjectCopy = spell.createCopyOnStack(game, source, source.getControllerId(), true);
                if (stackObjectCopy instanceof Spell) {
                    String activateMessage = ((Spell) stackObjectCopy).getActivatedMessage(game);
                    if (activateMessage.startsWith(" casts ")) {
                        activateMessage = activateMessage.substring(6);
                    }
                    game.informPlayers(controller.getLogName() + " copies " + activateMessage);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public CopySourceSpellEffect copy() {
        return new CopySourceSpellEffect(this);
    }
}
