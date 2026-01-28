package mage.cards.c;

import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
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
import mage.game.permanent.token.custom.CreatureToken;

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
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(filter))
                .withInterveningIf(CastFromEverywhereSourceCondition.instance));

        // Whenever Cacophony Unleashed or another enchantment you control enters, until end of turn, Cacophony Unleashed becomes a legendary 6/6 Nightmare God creature with menace and deathtouch. It's still an enchantment.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new BecomesCreatureSourceEffect(
                    new CreatureToken(
                        6, 6,
                        "legendary 6/6 Nightmare God creature with menace and deathtouch",
                        SubType.NIGHTMARE, SubType.GOD
                    ).withSuperType(SuperType.LEGENDARY).withAbility(new MenaceAbility()).withAbility(DeathtouchAbility.getInstance()),
                    CardType.ENCHANTMENT,
                    Duration.EndOfTurn
                ).setText("until end of turn, {this} becomes a legendary 6/6 Nightmare God "
                    + "creature with menace and deathtouch. It's still an enchantment."),
                StaticFilters.FILTER_PERMANENT_ENCHANTMENT,
                false, true
        ));
    }

    private CacophonyUnleashed(final CacophonyUnleashed card) {
        super(card);
    }

    @Override
    public CacophonyUnleashed copy() {
        return new CacophonyUnleashed(this);
    }
}
