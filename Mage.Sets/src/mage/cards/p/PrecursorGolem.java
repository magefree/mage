
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterInPlay;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GolemToken;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.util.TargetAddress;

/**
 * @author duncant
 */
public final class PrecursorGolem extends CardImpl {

    public PrecursorGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Precursor Golem enters the battlefield, create two 3/3 colorless Golem artifact creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GolemToken(), 2), false));

        // Whenever a player casts an instant or sorcery spell that targets only a single Golem, that player copies that spell for each other Golem that spell could target. Each copy targets a different one of those Golems.
        this.addAbility(new PrecursorGolemCopyTriggeredAbility());
    }

    public PrecursorGolem(final PrecursorGolem card) {
        super(card);
    }

    @Override
    public PrecursorGolem copy() {
        return new PrecursorGolem(this);
    }
}

class PrecursorGolemCopyTriggeredAbility extends TriggeredAbilityImpl {

    PrecursorGolemCopyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PrecursorGolemCopySpellEffect(), false);
    }

    PrecursorGolemCopyTriggeredAbility(final PrecursorGolemCopyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PrecursorGolemCopyTriggeredAbility copy() {
        return new PrecursorGolemCopyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return checkSpell(spell, game);
    }

    private boolean checkSpell(Spell spell, Game game) {
        if (spell != null
                && (spell.isInstant() || spell.isSorcery())) {
            UUID targetGolem = null;
            for (TargetAddress addr : TargetAddress.walk(spell)) {
                Target targetInstance = addr.getTarget(spell);
                for (UUID target : targetInstance.getTargets()) {
                    Permanent permanent = game.getPermanent(target);
                    if (permanent == null || !permanent.hasSubtype(SubType.GOLEM, game)) {
                        return false;
                    }
                    if (targetGolem == null) {
                        targetGolem = target;
                    } else // If a spell has multiple targets, but it's targeting the same Golem with all of them, Precursor Golem's last ability will trigger
                    {
                        if (!targetGolem.equals(target)) {
                            return false;
                        }
                    }
                }
            }
            if (targetGolem != null) {
                getEffects().get(0).setValue("triggeringSpell", spell);
                getEffects().get(0).setValue("targetedGolem", targetGolem);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts an instant or sorcery spell that targets only a single Golem, that player copies that spell for each other Golem that spell could target. Each copy targets a different one of those Golems.";
    }
}

class PrecursorGolemCopySpellEffect extends CopySpellForEachItCouldTargetEffect<Permanent> {

    public PrecursorGolemCopySpellEffect() {
        this(new FilterCreaturePermanent(SubType.GOLEM, "Golem"));
    }

    public PrecursorGolemCopySpellEffect(PrecursorGolemCopySpellEffect effect) {
        super(effect);
    }

    private PrecursorGolemCopySpellEffect(FilterInPlay<Permanent> filter) {
        super(filter);
    }

    @Override
    protected Player getPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    @Override
    protected Spell getSpell(Game game, Ability source) {
        return (Spell) getValue("triggeringSpell");
    }

    @Override
    protected boolean changeTarget(Target target, Game game, Ability source) {
        return true;
    }

    @Override
    protected void modifyCopy(Spell copy, Game game, Ability source) {
    }

    @Override
    public PrecursorGolemCopySpellEffect copy() {
        return new PrecursorGolemCopySpellEffect(this);
    }
}
