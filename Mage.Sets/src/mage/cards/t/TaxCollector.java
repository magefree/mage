package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DetainTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TaxCollector extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells");

    public TaxCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Tax Collector enters the battlefield, choose one
        // * Tax -- Until your next turn, spells your opponents cast cost {1} more to cast.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new SpellsCostIncreasingAllEffect(1, filter, TargetController.OPPONENT)
                        .setText("until your next turn, spells your opponents cast cost {1} more to cast")
                        .setDuration(Duration.UntilYourNextTurn)
        );
        ability.withFirstModeFlavorWord("Tax");

        // * Arrest -- Detain target creature an opponent controls.
        ability.addMode(new Mode(new DetainTargetEffect()).addTarget(new TargetOpponentsCreaturePermanent()).withFlavorWord("Arrest"));
        this.addAbility(ability);
    }

    private TaxCollector(final TaxCollector card) {
        super(card);
    }

    @Override
    public TaxCollector copy() {
        return new TaxCollector(this);
    }
}
