package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class BewitchingLeechcraft extends CardImpl {

    public BewitchingLeechcraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Tap));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Bewitching Leechcraft enters the battlefield, tap enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect()));

        // Enchanted creature has "If this creature would untap during your untap step, remove a +1/+1 counter from it instead. If you do, untap it."
        this.addAbility(new SimpleStaticAbility(
            new GainAbilityAttachedEffect(
                new SimpleStaticAbility(new BewitchingLeechcraftReplacementEffect()),
                AttachmentType.AURA
            )
        ));
    }

    private BewitchingLeechcraft(final BewitchingLeechcraft card) {
        super(card);
    }

    @Override
    public BewitchingLeechcraft copy() {
        return new BewitchingLeechcraft(this);
    }
}

class BewitchingLeechcraftReplacementEffect extends ReplacementEffectImpl {

    BewitchingLeechcraftReplacementEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "If this creature would untap during your untap step, " +
            "remove a +1/+1 counter from it instead. If you do, untap it.";
    }

    private BewitchingLeechcraftReplacementEffect(final BewitchingLeechcraftReplacementEffect effect) {
        super(effect);
    }

    @Override
    public BewitchingLeechcraftReplacementEffect copy() {
        return new BewitchingLeechcraftReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanentUntapping = game.getPermanent(source.getSourceId());
        if(!applies(event,source,game)){
            return false;
        }

        // If we could not remove a counter, we are replacing the UNTAP event.
        // If we could remove a counter, we are not replacing the UNTAP, just adding to it.
        return !permanentUntapping.getCounters(game).removeCounter(CounterType.P1P1, 1);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getTurnStepType() == PhaseStep.UNTAP
            && event.getTargetId().equals(source.getSourceId());
    }
}
