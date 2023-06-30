
package mage.cards.h;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 *
 * @author LoneFox
 *
 */
public final class HiddenAncients extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("enchantment spell");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public HiddenAncients(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");

        // When an opponent casts an enchantment spell, if Hidden Ancients is an enchantment, Hidden Ancients becomes a 5/5 Treefolk creature.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(new HiddenAncientsTreefolkToken(), null, Duration.WhileOnBattlefield),
                filter, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "When an opponent casts an enchantment spell, if {this} is an enchantment, {this} becomes a 5/5 Treefolk creature."));
    }

    private HiddenAncients(final HiddenAncients card) {
        super(card);
    }

    @Override
    public HiddenAncients copy() {
        return new HiddenAncients(this);
    }
}

class HiddenAncientsTreefolkToken extends TokenImpl {

    public HiddenAncientsTreefolkToken() {
        super("Treefolk", "5/5 Treefolk creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.TREEFOLK);
        power = new MageInt(5);
        toughness = new MageInt(5);
    }
    public HiddenAncientsTreefolkToken(final HiddenAncientsTreefolkToken token) {
        super(token);
    }

    public HiddenAncientsTreefolkToken copy() {
        return new HiddenAncientsTreefolkToken(this);
    }
}
