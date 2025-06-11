package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
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
public final class OpalChampion extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition(
            new FilterEnchantmentPermanent("this permanent is an enchantment")
    );

    public OpalChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When an opponent casts a creature spell, if Opal Champion is an enchantment, Opal Champion becomes a 3/3 Knight creature with first strike.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new BecomesCreatureSourceEffect(
                        new OpalChampionKnight(), null, Duration.WhileOnBattlefield
                ), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ).withInterveningIf(condition));
    }

    private OpalChampion(final OpalChampion card) {
        super(card);
    }

    @Override
    public OpalChampion copy() {
        return new OpalChampion(this);
    }
}

class OpalChampionKnight extends TokenImpl {

    public OpalChampionKnight() {
        super("Knight", "3/3 Knight creature with first strike");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private OpalChampionKnight(final OpalChampionKnight token) {
        super(token);
    }

    public OpalChampionKnight copy() {
        return new OpalChampionKnight(this);
    }
}
