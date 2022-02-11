package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class VeiledSentry extends CardImpl {

    public VeiledSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        // When an opponent casts a spell, if Veiled Sentry is an enchantment, Veiled Sentry becomes an Illusion creature with power and toughness each equal to that spell's converted mana cost.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new VeiledSentryEffect(), new FilterSpell(), false, SetTargetPointer.SPELL);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "Whenever an opponent casts a spell, if Veiled Sentry is an enchantment, Veil Sentry becomes an Illusion creature with power and toughness equal to that spell's mana value."));

    }

    private VeiledSentry(final VeiledSentry card) {
        super(card);
    }

    @Override
    public VeiledSentry copy() {
        return new VeiledSentry(this);
    }
}

class VeiledSentryEffect extends ContinuousEffectImpl {

    public VeiledSentryEffect() {
        super(Duration.Custom, Outcome.BecomeCreature);
        staticText = "{this} becomes an Illusion creature with power and toughness equal to that spell's mana value";
    }

    public VeiledSentryEffect(final VeiledSentryEffect effect) {
        super(effect);
    }

    @Override
    public VeiledSentryEffect copy() {
        return new VeiledSentryEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent veiledSentry = game.getPermanent(source.getSourceId());
        Spell spellCast = game.getSpell(targetPointer.getFirst(game, source));
        if (spellCast != null) {
            game.getState().setValue(source + "cmcSpell", spellCast.getManaValue());
        }
        if (veiledSentry == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                veiledSentry.removeAllCardTypes(game);
                veiledSentry.removeAllSubTypes(game);
                veiledSentry.addCardType(game, CardType.CREATURE);
                veiledSentry.addSubType(game, SubType.ILLUSION);
                break;

            case PTChangingEffects_7:
                if (game.getState().getValue(source + "cmcSpell") != null) {
                    int cmc = (int) game.getState().getValue(source + "cmcSpell");
                    if (sublayer == SubLayer.SetPT_7b) {
                        veiledSentry.addPower(cmc);
                        veiledSentry.addToughness(cmc);
                    }
                }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7
                || layer == Layer.TypeChangingEffects_4;
    }
}
