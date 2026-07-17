package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GeneralTazri extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("an Ally creature card");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.ALLY, "Ally creatures");

    static {
        filter.add(SubType.ALLY.getPredicate());
    }

    public GeneralTazri(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When General Tazri enters the battlefield, you may search your library for an Ally creature card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true
        ));

        // {W}{U}{B}{R}{G}: Ally creatures you control get +X/+X until end of turn, where X is the number of colors among those creatures.
        this.addAbility(new SimpleActivatedAbility(
                new BoostControlledEffect(
                        ColorsAmongControlledPermanentsCount.ALLIES,
                        ColorsAmongControlledPermanentsCount.ALLIES,
                        Duration.EndOfTurn, filter2, false
                ).setText("Ally creatures you control get +X/+X until end of turn, " +
                        "where X is the number of colors among those creatures"),
                new ManaCostsImpl<>("{W}{U}{B}{R}{G}")
        ).addHint(ColorsAmongControlledPermanentsCount.ALLIES.getHint()));

    }

    private GeneralTazri(final GeneralTazri card) {
        super(card);
    }

    @Override
    public GeneralTazri copy() {
        return new GeneralTazri(this);
    }
}
