package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
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
 * @author LoneFox
 */
public final class OpalCaryatid extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition(
            new FilterEnchantmentPermanent("this permanent is an enchantment")
    );

    public OpalCaryatid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // When an opponent casts a creature spell, if Opal Caryatid is an enchantment, Opal Caryatid becomes a 2/2 Soldier creature.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new BecomesCreatureSourceEffect(
                        new OpalCaryatidSoldierToken(), null, Duration.WhileOnBattlefield
                ), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ).withInterveningIf(condition));
    }

    private OpalCaryatid(final OpalCaryatid card) {
        super(card);
    }

    @Override
    public OpalCaryatid copy() {
        return new OpalCaryatid(this);
    }
}

class OpalCaryatidSoldierToken extends TokenImpl {

    public OpalCaryatidSoldierToken() {
        super("Soldier", "2/2 Soldier creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private OpalCaryatidSoldierToken(final OpalCaryatidSoldierToken token) {
        super(token);
    }

    public OpalCaryatidSoldierToken copy() {
        return new OpalCaryatidSoldierToken(this);
    }
}
