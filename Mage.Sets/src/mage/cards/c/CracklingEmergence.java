package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
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
public final class CracklingEmergence extends CardImpl {

    public CracklingEmergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant land you control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted land is a 3/3 red Spirit creature with haste. It's still a land.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
                new CreatureToken(3, 3)
                        .withColor("R")
                        .withSubType(SubType.SPIRIT)
                        .withAbility(HasteAbility.getInstance()),
                "Enchanted land is a 3/3 red Spirit creature with haste. It's still a land.",
                Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.COLOR
        )));

        // If enchanted land would be destroyed, instead sacrifice Crackling Emergence and that land gains indestructible until end of turn.
        this.addAbility(new SimpleStaticAbility(new CracklingEmergenceEffect()));
    }

    private CracklingEmergence(final CracklingEmergence card) {
        super(card);
    }

    @Override
    public CracklingEmergence copy() {
        return new CracklingEmergence(this);
    }
}

class CracklingEmergenceEffect extends ReplacementEffectImpl {

    CracklingEmergenceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if enchanted land would be destroyed, instead sacrifice " +
                "{this} and that land gains indestructible until end of turn";
    }

    private CracklingEmergenceEffect(final CracklingEmergenceEffect effect) {
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
    public CracklingEmergenceEffect copy() {
        return new CracklingEmergenceEffect(this);
    }
}
