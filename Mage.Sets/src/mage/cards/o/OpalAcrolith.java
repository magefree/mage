package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BecomesEnchantmentSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class OpalAcrolith extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition(
            new FilterEnchantmentPermanent("this permanent is an enchantment")
    );

    public OpalAcrolith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever an opponent casts a creature spell, if Opal Acrolith is an enchantment, Opal Acrolith becomes a 2/4 Soldier creature.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new BecomesCreatureSourceEffect(
                        new OpalAcrolithToken(), null, Duration.WhileOnBattlefield
                ), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ).withInterveningIf(condition));

        // {0}: Opal Acrolith becomes an enchantment.
        this.addAbility(new SimpleActivatedAbility(new BecomesEnchantmentSourceEffect(), new ManaCostsImpl<>("{0}")));
    }

    private OpalAcrolith(final OpalAcrolith card) {
        super(card);
    }

    @Override
    public OpalAcrolith copy() {
        return new OpalAcrolith(this);
    }
}

class OpalAcrolithToken extends TokenImpl {

    public OpalAcrolithToken() {
        super("Soldier", "2/4 Soldier creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(2);
        toughness = new MageInt(4);
    }

    private OpalAcrolithToken(final OpalAcrolithToken token) {
        super(token);
    }

    public OpalAcrolithToken copy() {
        return new OpalAcrolithToken(this);
    }
}
