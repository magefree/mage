package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DraftFromSpellbookEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IshkanahBroodmother extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SPIDER, "Spiders");
    private static final List<String> spellbook = Collections.unmodifiableList(Arrays.asList(
            "Arachnoform",
            "Brood Weaver",
            "Drider",
            // "Glowstone Recluse", mutate card
            "Gnottvold Recluse",
            "Hatchery Spider",
            "Mammoth Spider",
            "Netcaster Spider",
            "Prey Upon",
            "Sentinel Spider",
            "Snarespinner",
            "Spider Spawning",
            "Spidery Grasp",
            "Sporecap Spider",
            "Twin-Silk Spider"
    ));

    public IshkanahBroodmother(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Other Spiders you control get +1/+2.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 2, Duration.WhileOnBattlefield, filter, true
        )));

        // {1}{B/G}, Exile two cards from your graveyard: Draft a card from Ishkanah, Broodmother's spellbook.
        Ability ability = new SimpleActivatedAbility(
                new DraftFromSpellbookEffect(spellbook), new ManaCostsImpl<>("{1}{B/G}")
        );
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(
                2, StaticFilters.FILTER_CARD_CARDS
        )));
        this.addAbility(ability);
    }

    private IshkanahBroodmother(final IshkanahBroodmother card) {
        super(card);
    }

    @Override
    public IshkanahBroodmother copy() {
        return new IshkanahBroodmother(this);
    }
}
