package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
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
public final class OpalArchangel extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition(
            new FilterEnchantmentPermanent("this permanent is an enchantment")
    );

    public OpalArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // When an opponent casts a creature spell, if Opal Archangel is an enchantment, Opal Archangel becomes a 5/5 Angel creature with flying and vigilance.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new BecomesCreatureSourceEffect(
                        new OpalArchangelToken(), null, Duration.WhileOnBattlefield
                ), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ).withInterveningIf(condition));
    }

    private OpalArchangel(final OpalArchangel card) {
        super(card);
    }

    @Override
    public OpalArchangel copy() {
        return new OpalArchangel(this);
    }
}

class OpalArchangelToken extends TokenImpl {

    public OpalArchangelToken() {
        super("Angel", "5/5 Angel creature with flying and vigilance");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ANGEL);
        power = new MageInt(5);
        toughness = new MageInt(5);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
    }

    private OpalArchangelToken(final OpalArchangelToken token) {
        super(token);
    }

    public OpalArchangelToken copy() {
        return new OpalArchangelToken(this);
    }
}
