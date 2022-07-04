package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author jeffwadsworth
 */
public final class VeiledSerpent extends CardImpl {

    public VeiledSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // When an opponent casts a spell, if Veiled Serpent is an enchantment, Veiled Serpent becomes a 4/4 Serpent creature that can't attack unless defending player controls an Island.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(new VeiledSerpentToken(), "", Duration.WhileOnBattlefield, true, false),
                new FilterSpell(), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "Whenever an opponent casts a spell, if Veiled Serpent is an enchantment, Veiled Serpent becomes a 4/4 Serpent creature that can't attack unless defending player controls an Island."));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private VeiledSerpent(final VeiledSerpent card) {
        super(card);
    }

    @Override
    public VeiledSerpent copy() {
        return new VeiledSerpent(this);
    }
}

class VeiledSerpentToken extends TokenImpl {

    public VeiledSerpentToken() {
        super("Serpent", "4/4 Serpent creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SERPENT);
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new CantAttackUnlessDefenderControllsPermanent(
                        new FilterLandPermanent(SubType.ISLAND, "an Island"))));
    }

    public VeiledSerpentToken(final VeiledSerpentToken token) {
        super(token);
    }

    public VeiledSerpentToken copy() {
        return new VeiledSerpentToken(this);
    }
}
