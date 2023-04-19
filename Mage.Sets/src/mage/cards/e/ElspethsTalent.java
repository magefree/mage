package mage.cards.e;

import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.game.stack.StackObject;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlaneswalkerPermanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElspethsTalent extends CardImpl {

    public ElspethsTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant planeswalker
        TargetPermanent auraTarget = new TargetPlaneswalkerPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted planeswalker has "[+1]: Create three 1/1 white Soldier creature tokens."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new LoyaltyAbility(new CreateTokenEffect(new SoldierToken(), 3), 1),
                AttachmentType.AURA, Duration.WhileOnBattlefield, null, "planeswalker"
        )));

        // Whenever you activate a loyalty ability of enchanted planeswalker, creatures you control get +2/+2 and gain vigilance until end of turn.
        this.addAbility(new ElspethsTalentTriggeredAbility());
    }

    private ElspethsTalent(final ElspethsTalent card) {
        super(card);
    }

    @Override
    public ElspethsTalent copy() {
        return new ElspethsTalent(this);
    }
}

class ElspethsTalentTriggeredAbility extends TriggeredAbilityImpl {

    ElspethsTalentTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostControlledEffect(2, 2, Duration.EndOfTurn));
        this.addEffect(new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE));
    }

    private ElspethsTalentTriggeredAbility(final ElspethsTalentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ElspethsTalentTriggeredAbility copy() {
        return new ElspethsTalentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        return permanent != null
                && event.getSourceId().equals(permanent.getAttachedTo())
                && isControlledBy(event.getPlayerId())
                && Optional.ofNullable(game.getStack().getStackObject(event.getSourceId()))
                .filter(Objects::nonNull)
                .map(StackObject::getStackAbility)
                .map(LoyaltyAbility.class::isInstance)
                .orElse(false);
    }

    @Override
    public String getRule() {
        return "Whenever you activate a loyalty ability of enchanted planeswalker, " +
                "creatures you control get +2/+2 and gain vigilance until end of turn.";
    }
}
