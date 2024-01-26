package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AirtightAlibi extends CardImpl {

    public AirtightAlibi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Airtight Alibi enters the battlefield, untap enchanted creature. It gains hexproof until end of turn. If it's suspected, it's no longer suspected.
        Ability ability = new EntersBattlefieldTriggeredAbility(new UntapAttachedEffect());
        ability.addEffect(new GainAbilityAttachedEffect(
                HexproofAbility.getInstance(), AttachmentType.AURA, Duration.EndOfTurn
        ).setText("It gains hexproof until end of turn"));
        ability.addEffect(new AirtightAlibiBecomeSuspectedEffect());
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 and can't become suspected.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2));
        ability.addEffect(new AirtightAlibiReplacementEffect());
        this.addAbility(ability);
    }

    private AirtightAlibi(final AirtightAlibi card) {
        super(card);
    }

    @Override
    public AirtightAlibi copy() {
        return new AirtightAlibi(this);
    }
}

class AirtightAlibiBecomeSuspectedEffect extends OneShotEffect {

    AirtightAlibiBecomeSuspectedEffect() {
        super(Outcome.Benefit);
        staticText = "If it's suspected, it's no longer suspected";
    }

    private AirtightAlibiBecomeSuspectedEffect(final AirtightAlibiBecomeSuspectedEffect effect) {
        super(effect);
    }

    @Override
    public AirtightAlibiBecomeSuspectedEffect copy() {
        return new AirtightAlibiBecomeSuspectedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .orElse(null);
        if (permanent == null || !permanent.isSuspected()) {
            return false;
        }
        permanent.setSuspected(false, game, source);
        return true;
    }
}

class AirtightAlibiReplacementEffect extends ReplacementEffectImpl {

    AirtightAlibiReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "and can't become suspected";
    }

    private AirtightAlibiReplacementEffect(final AirtightAlibiReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOME_SUSPECTED;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(Permanent::getAttachedTo)
                .map(event.getTargetId()::equals)
                .orElse(false);
    }

    @Override
    public AirtightAlibiReplacementEffect copy() {
        return new AirtightAlibiReplacementEffect(this);
    }
}
