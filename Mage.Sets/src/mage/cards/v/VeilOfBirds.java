package mage.cards.v;

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
 * @author jeffwadsworth
 */
public final class VeilOfBirds extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition(new FilterEnchantmentPermanent("{this} is an enchantment"));

    public VeilOfBirds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        // When an opponent casts a spell, if Veil of Birds is an enchantment, Veil of Birds becomes a 1/1 Bird creature with flying.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(
                new VeilOfBirdsToken(), null, Duration.WhileOnBattlefield
        ), StaticFilters.FILTER_SPELL_A, false).withInterveningIf(condition));
    }

    private VeilOfBirds(final VeilOfBirds card) {
        super(card);
    }

    @Override
    public VeilOfBirds copy() {
        return new VeilOfBirds(this);
    }
}

class VeilOfBirdsToken extends TokenImpl {

    public VeilOfBirdsToken() {
        super("Bird", "1/1 creature with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    private VeilOfBirdsToken(final VeilOfBirdsToken token) {
        super(token);
    }

    public VeilOfBirdsToken copy() {
        return new VeilOfBirdsToken(this);
    }
}
