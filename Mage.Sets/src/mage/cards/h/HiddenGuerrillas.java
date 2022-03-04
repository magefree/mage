
package mage.cards.h;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactSpell;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 *
 * @author LoneFox
 *
 */
public final class HiddenGuerrillas extends CardImpl {

    public HiddenGuerrillas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");

        // When an opponent casts an artifact spell, if Hidden Guerrillas is an enchantment, Hidden Guerrillas becomes a 5/3 Soldier creature with trample.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(new HiddenGuerrillasSoldier(), "", Duration.WhileOnBattlefield, true, false),
                new FilterArtifactSpell(), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "When an opponent casts an artifact spell, if {this} is an enchantment, {this} becomes a 5/3 Soldier creature with trample."));
    }

    private HiddenGuerrillas(final HiddenGuerrillas card) {
        super(card);
    }

    @Override
    public HiddenGuerrillas copy() {
        return new HiddenGuerrillas(this);
    }
}

class HiddenGuerrillasSoldier extends TokenImpl {

    public HiddenGuerrillasSoldier() {
        super("Soldier", "5/3 Soldier creature with trample");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(5);
        toughness = new MageInt(3);
        this.addAbility(TrampleAbility.getInstance());
    }
    public HiddenGuerrillasSoldier(final HiddenGuerrillasSoldier token) {
        super(token);
    }

    public HiddenGuerrillasSoldier copy() {
        return new HiddenGuerrillasSoldier(this);
    }
}
