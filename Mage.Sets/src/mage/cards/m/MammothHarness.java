package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class MammothHarness extends CardImpl {

    public MammothHarness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.LoseAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature loses flying.
        this.addAbility(new SimpleStaticAbility(new LoseAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA)));

        // Whenever enchanted creature blocks or becomes blocked by a creature, the other creature gains first strike until end of turn.
        this.addAbility(new MammothHarnessTriggeredAbility());
    }

    private MammothHarness(final MammothHarness card) {
        super(card);
    }

    @Override
    public MammothHarness copy() {
        return new MammothHarness(this);
    }
}

class MammothHarnessTriggeredAbility extends TriggeredAbilityImpl {

    public MammothHarnessTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), false);
    }

    public MammothHarnessTriggeredAbility(final MammothHarnessTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent aura = getSourcePermanentIfItStillExists(game);
        if (aura == null) {
            return false;
        }
        Permanent otherCreature = null;
        if (event.getSourceId().equals(aura.getAttachedTo())) {
            otherCreature = game.getPermanent(event.getTargetId());
        } else if (event.getTargetId().equals(aura.getAttachedTo())) {
            otherCreature = game.getPermanent(event.getSourceId());
        }
        if (otherCreature == null) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(otherCreature, game));
        return true;
    }

    @Override
    public String getRule() {
        return " Whenever enchanted creature blocks or becomes blocked by a creature, the other creature gains first strike until end of turn.";
    }

    @Override
    public MammothHarnessTriggeredAbility copy() {
        return new MammothHarnessTriggeredAbility(this);
    }
}
