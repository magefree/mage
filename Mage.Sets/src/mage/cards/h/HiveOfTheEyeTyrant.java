package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.card.DefendingPlayerOwnsCardPredicate;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HiveOfTheEyeTyrant extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_LAND, ComparisonType.MORE_THAN, 1
    );
    private static final FilterCard filter = new FilterCard("card from defending player's graveyard");

    static {
        filter.add(DefendingPlayerOwnsCardPredicate.instance);
    }

    public HiveOfTheEyeTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // If you control two or more other lands, Hive of the Eye Tyrant enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldAbility(
                new TapSourceEffect(), condition, "If you control two or more other lands, " +
                "{this} enters the battlefield tapped.", ""
        ));

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {3}{B}: Until end of turn, Hive of the Eye Tyrant becomes a 3/3 black Beholder creature with menace and "Whenever this creature attacks, exile target card from defending player's graveyard." It's still a land.
        Ability ability = new AttacksTriggeredAbility(
                new ExileTargetEffect(), false,
                "Whenever this creature attacks, exile target card from defending player's graveyard."
        );
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        3, 3, "3/3 black Beholder creature with menace and " +
                        "\"Whenever this creature attacks, exile target card from defending player's graveyard.\""
                ).withSubType(SubType.BEHOLDER).withColor("B").withAbility(new MenaceAbility()).withAbility(ability),
                CardType.LAND, Duration.EndOfTurn
        ).withDurationRuleAtStart(true), new ManaCostsImpl<>("{3}{B}")));
    }

    private HiveOfTheEyeTyrant(final HiveOfTheEyeTyrant card) {
        super(card);
    }

    @Override
    public HiveOfTheEyeTyrant copy() {
        return new HiveOfTheEyeTyrant(this);
    }
}
