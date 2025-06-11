package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class OpalGargoyle extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition(
            new FilterEnchantmentPermanent("this permanent is an enchantment")
    );

    public OpalGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // When an opponent casts a creature spell, if Opal Gargoyle is an enchantment, Opal Gargoyle becomes a 2/2 Gargoyle creature with flying.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new BecomesCreatureSourceEffect(
                        new OpalGargoyleToken(), null, Duration.WhileOnBattlefield
                ), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ).withInterveningIf(condition));
    }

    private OpalGargoyle(final OpalGargoyle card) {
        super(card);
    }

    @Override
    public OpalGargoyle copy() {
        return new OpalGargoyle(this);
    }
}

class OpalGargoyleToken extends TokenImpl {

    public OpalGargoyleToken() {
        super("Gargoyle", "2/2 Gargoyle creature with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GARGOYLE);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }

    private OpalGargoyleToken(final OpalGargoyleToken token) {
        super(token);
    }

    public OpalGargoyleToken copy() {
        return new OpalGargoyleToken(this);
    }
}
