package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HakodaSelflessCommander extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.ALLY, "cast Ally spells");

    public HakodaSelflessCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast Ally spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayFromTopOfLibraryEffect(filter)));

        // Sacrifice Hakoda: Creatures you control get +0/+5 and gain indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostControlledEffect(0, 5, Duration.EndOfTurn)
                        .setText("creatures you control get +0/+5"),
                new SacrificeSourceCost()
        );
        ability.addEffect(new GainAbilityAllEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("and gain indestructible until end of turn"));
        this.addAbility(ability);
    }

    private HakodaSelflessCommander(final HakodaSelflessCommander card) {
        super(card);
    }

    @Override
    public HakodaSelflessCommander copy() {
        return new HakodaSelflessCommander(this);
    }
}
