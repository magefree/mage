package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.PTChangingEffects_7;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

/**
 *
 * @author jeffwadsworth
 */
public final class VeiledSentry extends CardImpl {

    public VeiledSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        // When an opponent casts a spell, if Veiled Sentry is an enchantment, Veiled Sentry becomes an Illusion creature with power and toughness each equal to that spell's converted mana cost.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new VeiledSentryEffect(), new FilterSpell(), false, SetTargetPointer.SPELL);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_ENCHANTMENT_PERMANENT),
                "Whenever an opponent casts a spell, if Veiled Sentry is an enchantment, Veil Sentry becomes an Illusion creature with power and toughness equal to that spell's converted mana cost."));

    }

    public VeiledSentry(final VeiledSentry card) {
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
        staticText = "{this} becomes an Illusion creature with power and toughness equal to that spell's converted mana cost";
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
            game.getState().setValue(source + "cmcSpell", spellCast.getConvertedManaCost());
        }
        if (veiledSentry == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                if (sublayer == SubLayer.NA) {
                    veiledSentry.getCardType().clear();
                    if (!veiledSentry.isCreature()) {
                        veiledSentry.addCardType(CardType.CREATURE);
                    }
                    if (!veiledSentry.getSubtype(game).contains(SubType.ILLUSION)) {
                        veiledSentry.getSubtype(game).add(SubType.ILLUSION);
                    }
                }
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
