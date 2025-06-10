package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class HiddenGuerrillas extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition("{this} is an enchantment", StaticFilters.FILTER_PERMANENT_ENCHANTMENT);

    public HiddenGuerrillas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // When an opponent casts an artifact spell, if Hidden Guerrillas is an enchantment, Hidden Guerrillas becomes a 5/3 Soldier creature with trample.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(
                new HiddenGuerrillasSoldier(), null, Duration.WhileOnBattlefield
        ), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false).withInterveningIf(condition).setTriggerPhrase("When an opponent casts an artifact spell, "));
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

    private HiddenGuerrillasSoldier(final HiddenGuerrillasSoldier token) {
        super(token);
    }

    public HiddenGuerrillasSoldier copy() {
        return new HiddenGuerrillasSoldier(this);
    }
}
