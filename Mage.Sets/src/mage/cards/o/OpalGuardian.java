package mage.cards.o;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
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
public final class OpalGuardian extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition(
            new FilterEnchantmentPermanent("this permanent is an enchantment")
    );

    public OpalGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{W}{W}");

        // When an opponent casts a creature spell, if Opal Guardian is an enchantment, Opal Guardian becomes a 3/4 Gargoyle creature with flying and protection from red.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new BecomesCreatureSourceEffect(
                        new OpalGuardianGargoyle(), null, Duration.WhileOnBattlefield
                ), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ).withInterveningIf(condition));
    }

    private OpalGuardian(final OpalGuardian card) {
        super(card);
    }

    @Override
    public OpalGuardian copy() {
        return new OpalGuardian(this);
    }
}

class OpalGuardianGargoyle extends TokenImpl {
    public OpalGuardianGargoyle() {
        super("Gargoyle", "3/4 Gargoyle creature with flying and protection from red");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GARGOYLE);
        power = new MageInt(3);
        toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
    }

    private OpalGuardianGargoyle(final OpalGuardianGargoyle token) {
        super(token);
    }

    public OpalGuardianGargoyle copy() {
        return new OpalGuardianGargoyle(this);
    }
}
