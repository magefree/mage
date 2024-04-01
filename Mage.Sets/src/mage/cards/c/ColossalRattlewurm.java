package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ColossalRattlewurm extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.DESERT));
    private static final Hint hint = new ConditionHint(condition, "You control a Desert");
    private static final FilterCard filter = new FilterCard("a Desert card");

    static {
        filter.add(SubType.DESERT.getPredicate());
    }

    public ColossalRattlewurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Colossal Rattlewurm has flash as long as you control a Desert.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                        FlashAbility.getInstance(), Duration.WhileOnBattlefield, true
                ), condition, "{this} has flash as long as you control a Desert")
        ).setRuleAtTheTop(true).addHint(hint));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {1}{G}, Exile Colossal Rattlewurm from your graveyard: Search your library for a Desert card, put it onto the battlefield tapped, then shuffle.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true),
                new ManaCostsImpl<>("{1}{G}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private ColossalRattlewurm(final ColossalRattlewurm card) {
        super(card);
    }

    @Override
    public ColossalRattlewurm copy() {
        return new ColossalRattlewurm(this);
    }
}
