package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CacophonyUnleashed extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nonenchantment creatures");

    static {
        filter.add(Predicates.not(CardType.ENCHANTMENT.getPredicate()));
    }

    public CacophonyUnleashed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{B}{B}");


        // When Cacophony Unleashed enters the battlefield, if you cast it, destroy all nonenchantment creatures.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
            new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(filter)),
            CastFromEverywhereSourceCondition.instance,
            "When {this} enters the battlefield, if you cast it, destroy all nonenchantment creatures."
        ));

        // Whenever Cacophony Unleashed or another enchantment enters the battlefield under your control, until end of turn, Cacophony Unleashed becomes a legendary 6/6 Nightmare God creature with menace and deathtouch. It's still an enchantment.
        this.addAbility(
            new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new BecomesCreatureSourceEffect(new CacophonyUnleashedToken(), CardType.ENCHANTMENT, Duration.EndOfTurn)
                    .setText("until end of turn, Cacophony Unleashed becomes a legendary 6/6 Nightmare God "
                        + "creature with menace and deathtouch. It's still an enchantment."),
                StaticFilters.FILTER_PERMANENT_ENCHANTMENT,
                false, true
            )
        );
    }

    private CacophonyUnleashed(final CacophonyUnleashed card) {
        super(card);
    }

    @Override
    public CacophonyUnleashed copy() {
        return new CacophonyUnleashed(this);
    }
}

class CacophonyUnleashedToken extends TokenImpl {

    CacophonyUnleashedToken() {
        super("", "legendary 6/6 Nightmare God creature with menace and deathtouch");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.NIGHTMARE);
        subtype.add(SubType.GOD);
        power = new MageInt(6);
        toughness = new MageInt(6);
        addAbility(new MenaceAbility(false));
        addAbility(DeathtouchAbility.getInstance());
    }

    private CacophonyUnleashedToken(final CacophonyUnleashedToken token) {
        super(token);
    }

    public CacophonyUnleashedToken copy() {
        return new CacophonyUnleashedToken(this);
    }
}
