package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author North, noxx
 */
public final class HowlpackAlpha extends CardImpl {

    private static final String ruleText = "At the beginning of your end step, create a 2/2 green Wolf creature token";

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Werewolf and Wolf creatures");

    static {
        filter.add(Predicates.or(
                SubType.WEREWOLF.getPredicate(),
                SubType.WOLF.getPredicate()
        ));
    }

    public HowlpackAlpha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Werewolf and Wolf creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // At the beginning of your end step, create a 2/2 green Wolf creature token.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new CreateTokenEffect(new WolfToken()), false));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Howlpack Alpha.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private HowlpackAlpha(final HowlpackAlpha card) {
        super(card);
    }

    @Override
    public HowlpackAlpha copy() {
        return new HowlpackAlpha(this);
    }
}
