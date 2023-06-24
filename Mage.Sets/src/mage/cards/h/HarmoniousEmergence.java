package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarmoniousEmergence extends CardImpl {

    public HarmoniousEmergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant land you control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted land is a 4/5 green Spirit creature with vigilance and haste. It's still a land.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
                new CreatureToken(4, 5)
                        .withColor("G")
                        .withSubType(SubType.SPIRIT)
                        .withAbility(VigilanceAbility.getInstance())
                        .withAbility(HasteAbility.getInstance()),
                "enchanted land is a 4/5 green Spirit creature with vigilance and haste. It's still a land",
                Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.COLOR
        )));

        // If enchanted land would be destroyed, instead sacrifice Harmonious Emergence and that land gains indestructible until end of turn.
        this.addAbility(new SimpleStaticAbility(new HarmoniousEmergenceEffect()));
    }

    private HarmoniousEmergence(final HarmoniousEmergence card) {
        super(card);
    }

    @Override
    public HarmoniousEmergence copy() {
        return new HarmoniousEmergence(this);
    }
}

class HarmoniousEmergenceEffect extends ReplacementEffectImpl {

    HarmoniousEmergenceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if enchanted land would be destroyed, instead sacrifice " +
                "{this} and that land gains indestructible until end of turn";
    }

    private HarmoniousEmergenceEffect(final HarmoniousEmergenceEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent enchantedPermanent = game.getPermanent(event.getTargetId());
        if (sourcePermanent == null || enchantedPermanent == null) {
            return false;
        }
        sourcePermanent.sacrifice(source, game);
        game.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setTargetPointer(new FixedTarget(enchantedPermanent, game)), source);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROY_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        return sourcePermanent != null && event.getTargetId().equals(sourcePermanent.getAttachedTo());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HarmoniousEmergenceEffect copy() {
        return new HarmoniousEmergenceEffect(this);
    }
}
