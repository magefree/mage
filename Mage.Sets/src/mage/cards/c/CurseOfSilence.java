package mage.cards.c;

import java.util.UUID;

import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class CurseOfSilence extends CardImpl {

    public CurseOfSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // As Curse of Silence enters the battlefield, choose a card name.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL)));

        // Spells with the chosen name enchanted player casts cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(new CurseOfSilenceCostEffect()));

        // Whenever enchanted player casts a spell with the chosen name, you may sacrifice Curse of Silence. If you do, draw a card.
        this.addAbility(new CurseOfSilenceTriggeredAbility());
    }

    private CurseOfSilence(final CurseOfSilence card) {
        super(card);
    }

    @Override
    public CurseOfSilence copy() {
        return new CurseOfSilence(this);
    }
}

class CurseOfSilenceCostEffect extends CostModificationEffectImpl {

    public CurseOfSilenceCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.INCREASE_COST);
        staticText = "Spells with the chosen name enchanted player casts cost {2} more to cast";
    }

    private CurseOfSilenceCostEffect(final CurseOfSilenceCostEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfSilenceCostEffect copy() {
        return new CurseOfSilenceCostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Permanent enchantment = source.getSourcePermanentIfItStillExists(game);
            if (enchantment != null && abilityToModify.isControlledBy(enchantment.getAttachedTo())) {
                Card card = game.getCard(abilityToModify.getSourceId());
                String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
                if (card != null && cardName != null && !cardName.isEmpty()) {
                    return CardUtil.haveSameNames(card, cardName, game);
                }
            }
        }
        return false;
    }
}

class CurseOfSilenceTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfSilenceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new SacrificeSourceCost()
        ));
        setTriggerPhrase("Whenever enchanted player casts a spell with the chosen name, ");
    }

    private CurseOfSilenceTriggeredAbility(final CurseOfSilenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfSilenceTriggeredAbility copy() {
        return new CurseOfSilenceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = getSourcePermanentIfItStillExists(game);
        if (enchantment != null && event.getPlayerId().equals(enchantment.getAttachedTo())) {
            Card card = game.getCard(event.getSourceId());
            String cardName = (String) game.getState().getValue(sourceId.toString() + ChooseACardNameEffect.INFO_KEY);
            if (card != null && cardName != null && !cardName.isEmpty()) {
                return CardUtil.haveSameNames(card, cardName, game);
            }
        }
        return false;
    }
}
