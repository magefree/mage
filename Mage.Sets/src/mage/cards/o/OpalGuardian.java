package mage.cards.o;

import mage.ObjectColor;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceIsEnchantmentCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class OpalGuardian extends CardImpl {

    public OpalGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{W}{W}");

        // When an opponent casts a creature spell, if Opal Guardian is an enchantment, Opal Guardian becomes a 3/4 Gargoyle creature with flying and protection from red.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        3, 4, "3/4 Gargoyle creature with " +
                        "flying and protection from red", SubType.GARGOYLE
                ).withAbility(FlyingAbility.getInstance()).withAbility(ProtectionAbility.from(ObjectColor.RED)),
                null, Duration.WhileOnBattlefield
        ), StaticFilters.FILTER_SPELL_A_CREATURE, false)
                .withInterveningIf(SourceIsEnchantmentCondition.instance)
                .setTriggerPhrase("When an opponent casts a creature spell, "));
    }

    private OpalGuardian(final OpalGuardian card) {
        super(card);
    }

    @Override
    public OpalGuardian copy() {
        return new OpalGuardian(this);
    }
}
