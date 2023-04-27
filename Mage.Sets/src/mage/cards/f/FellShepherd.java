package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandFromGraveyardAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FellShepherd extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard();

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    public FellShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(8);
        this.toughness = new MageInt(6);

        // Whenever Fell Shepherd deals combat damage to a player, you may return to your hand all creature cards that were put into your graveyard from the battlefield this turn.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new ReturnToHandFromGraveyardAllEffect(filter, TargetController.YOU)
                        .setText("return to your hand all creature cards that were " +
                                "put into your graveyard from the battlefield this turn"),
                true
        ), new CardsPutIntoGraveyardWatcher());

        // {B}, Sacrifice another creature: Target creature gets -2/-2 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(-2, -2, Duration.EndOfTurn), new ManaCostsImpl<>("{B}")
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FellShepherd(final FellShepherd card) {
        super(card);
    }

    @Override
    public FellShepherd copy() {
        return new FellShepherd(this);
    }
}
