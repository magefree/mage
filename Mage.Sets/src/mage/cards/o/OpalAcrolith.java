package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BecomesEnchantmentSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author jeffwadsworth
 */
public final class OpalAcrolith extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature spell");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public OpalAcrolith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever an opponent casts a creature spell, if Opal Acrolith is an enchantment, Opal Acrolith becomes a 2/4 Soldier creature.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(new OpalAcrolithToken(), "", Duration.WhileOnBattlefield, true, false),
                filter, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "Whenever an opponent casts a creature spell, if Opal Acrolith is an enchantment, Opal Acrolith becomes a 2/4 Soldier creature."));

        // {0}: Opal Acrolith becomes an enchantment.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesEnchantmentSourceEffect(), new ManaCostsImpl<>("{0}")));

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

    public OpalAcrolithToken(final OpalAcrolithToken token) {
        super(token);
    }

    public OpalAcrolithToken copy() {
        return new OpalAcrolithToken(this);
    }
}
