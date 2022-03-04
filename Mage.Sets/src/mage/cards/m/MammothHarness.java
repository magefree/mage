package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBecomesBlockedSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
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
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature loses flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LoseAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA)));

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

class MammothHarnessTriggeredAbility extends BlocksOrBecomesBlockedSourceTriggeredAbility {

    public MammothHarnessTriggeredAbility() {
        super(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), StaticFilters.FILTER_PERMANENT_CREATURE, false, null, false);
    }

    public MammothHarnessTriggeredAbility(final MammothHarnessTriggeredAbility ability) {
        super(ability);

    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(this.getSourceId());
        if (sourcePermanent != null) {
            Permanent attachedTo = game.getPermanentOrLKIBattlefield(sourcePermanent.getAttachedTo());
            if (event.getSourceId().equals(attachedTo.getId())) {
                Permanent blocked = game.getPermanent(event.getTargetId());
                if (blocked != null && filter.match(blocked, game)) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                    return true;
                }
            }
            if (event.getTargetId().equals(attachedTo.getId())) {
                Permanent blocker = game.getPermanent(event.getSourceId());
                if (blocker != null) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
                    return true;
                }
            }
        }
        return false;
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
