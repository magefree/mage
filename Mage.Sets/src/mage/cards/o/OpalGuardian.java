
package mage.cards.o;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureSpell;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 *
 * @author LoneFox
 *
 */
public final class OpalGuardian extends CardImpl {

    public OpalGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}{W}{W}");

        // When an opponent casts a creature spell, if Opal Guardian is an enchantment, Opal Guardian becomes a 3/4 Gargoyle creature with flying and protection from red.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(new OpalGuardianGargoyle(), null, Duration.WhileOnBattlefield),
                new FilterCreatureSpell(), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "When an opponent casts a creature spell, if {this} is an enchantment, {this} becomes a 3/4 Gargoyle creature with flying and protection from red."));
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
    public OpalGuardianGargoyle(final OpalGuardianGargoyle token) {
        super(token);
    }

    public OpalGuardianGargoyle copy() {
        return new OpalGuardianGargoyle(this);
    }
}
