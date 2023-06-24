package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class VeiledSentry extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT);

    public VeiledSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        // When an opponent casts a spell, if Veiled Sentry is an enchantment, Veiled Sentry becomes an Illusion creature with power and toughness each equal to that spell's converted mana cost.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new SpellCastOpponentTriggeredAbility(new VeiledSentryEffect(), false),
                condition, "Whenever an opponent casts a spell, if {this} is an enchantment, " +
                "{this} becomes an Illusion creature with power and toughness equal to that spell's mana value."
        ));
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

    private int spellMV = 0;

    public VeiledSentryEffect() {
        super(Duration.Custom, Outcome.BecomeCreature);
        staticText = "{this} becomes an Illusion creature with power and toughness equal to that spell's mana value";
    }

    public VeiledSentryEffect(final VeiledSentryEffect effect) {
        super(effect);
        this.spellMV = effect.spellMV;
    }

    @Override
    public VeiledSentryEffect copy() {
        return new VeiledSentryEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Spell spell = (Spell) getValue("spellCast");
        if (spell != null) {
            spellMV = spell.getManaValue();
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeAllCardTypes(game);
                permanent.removeAllSubTypes(game);
                permanent.addCardType(game, CardType.CREATURE);
                permanent.addSubType(game, SubType.ILLUSION);
                break;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setModifiedBaseValue(spellMV);
                    permanent.getToughness().setModifiedBaseValue(spellMV);
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
