package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RowansTalent extends CardImpl {

    public RowansTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant planeswalker
        TargetPermanent auraTarget = new TargetPlaneswalkerPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted planeswalker has "[+1]: Up to one target creature gets +2/+0 and gains first strike and trample until end of turn."
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(2, 0)
                .setText("up to one target creature gets +2/+0"), 1);
        ability.addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())
                .setText("and gains first strike"));
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and trample until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                ability, AttachmentType.AURA, Duration.WhileOnBattlefield,
                null, "planeswalker"
        )));

        // Whenever you activate a loyalty ability of enchanted planeswalker, copy that ability. You may choose new targets for the copy.
        this.addAbility(new RowansTalentTriggeredAbility());
    }

    private RowansTalent(final RowansTalent card) {
        super(card);
    }

    @Override
    public RowansTalent copy() {
        return new RowansTalent(this);
    }
}

class RowansTalentTriggeredAbility extends TriggeredAbilityImpl {

    RowansTalentTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopyStackObjectEffect());
    }

    private RowansTalentTriggeredAbility(final RowansTalentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RowansTalentTriggeredAbility copy() {
        return new RowansTalentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        if (permanent == null
                || !event.getSourceId().equals(permanent.getAttachedTo())
                || !isControlledBy(event.getPlayerId())) {
            return false;
        }
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject == null || !(stackObject.getStackAbility() instanceof LoyaltyAbility)) {
            return false;
        }
        this.getEffects().setValue("stackObject", stackObject);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you activate a loyalty ability of enchanted planeswalker, " +
                "copy that ability. You may choose new targets for the copy.";
    }
}
