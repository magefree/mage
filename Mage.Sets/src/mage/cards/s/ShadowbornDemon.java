package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ShadowbornDemon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Demon creature");

    static {
        filter.add(Predicates.not(SubType.DEMON.getPredicate()));
    }

    private static final Condition condition = new InvertCondition(
            new CardsInControllerGraveyardCondition(6, StaticFilters.FILTER_CARD_CREATURE),
            "there are fewer than six creature cards in your graveyard"
    );

    public ShadowbornDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Shadowborn Demon enters the battlefield, destroy target non-Demon creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // At the beginning of your upkeep, if there are fewer than six creature cards in your graveyard, sacrifice a creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeControllerEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, 1, ""
        )).withInterveningIf(condition));
    }

    private ShadowbornDemon(final ShadowbornDemon card) {
        super(card);
    }

    @Override
    public ShadowbornDemon copy() {
        return new ShadowbornDemon(this);
    }
}
