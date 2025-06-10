package mage.cards.h;

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
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class HiddenAncients extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition("{this} is an enchantment", StaticFilters.FILTER_PERMANENT_ENCHANTMENT);

    public HiddenAncients(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // When an opponent casts an enchantment spell, if Hidden Ancients is an enchantment, Hidden Ancients becomes a 5/5 Treefolk creature.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(
                new HiddenAncientsTreefolkToken(), null, Duration.WhileOnBattlefield
        ), StaticFilters.FILTER_SPELL_AN_ENCHANTMENT, false).withInterveningIf(condition).setTriggerPhrase("When an opponent casts an enchantment spell, "));
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

    private HiddenAncientsTreefolkToken(final HiddenAncientsTreefolkToken token) {
        super(token);
    }

    public HiddenAncientsTreefolkToken copy() {
        return new HiddenAncientsTreefolkToken(this);
    }
}
