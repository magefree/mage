package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.Elemental11BlueRedToken;
import mage.game.stack.Spell;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrismariPianist extends CardImpl {

    public PrismariPianist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast an instant or sorcery spell, create a 1/1 blue and red Elemental creature token. If that spell's mana value is 5 or greater, create three of those tokens instead.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ConditionalOneShotEffect(
                new CreateTokenEffect(new Elemental11BlueRedToken(), 3),
                new CreateTokenEffect(new Elemental11BlueRedToken()),
                PrismariPianistCondition.instance, "create a 1/1 blue and red Elemental creature token. " +
                "If that spell's mana value is 5 or greater, create three of those tokens instead"
        ), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false));
    }

    private PrismariPianist(final PrismariPianist card) {
        super(card);
    }

    @Override
    public PrismariPianist copy() {
        return new PrismariPianist(this);
    }
}

enum PrismariPianistCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .getEffectValueFromAbility(source, "spellCast", Spell.class)
                .map(Spell::getManaValue)
                .filter(x -> x >= 5)
                .isPresent();
    }
}
