
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MarshalsAnthem extends CardImpl {

    private static final String rule = "return up to X target creature cards from your graveyard to the battlefield, " +
            "where X is the number of times {this} was kicked";

    public MarshalsAnthem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Multikicker {1}{W}
        this.addAbility(new MultikickerAbility("{1}{W}"));

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)
        ));

        // When Marshal's Anthem enters the battlefield, return up to X target creature cards from your graveyard to the battlefield, where X is the number of times Marshal's Anthem was kicked.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect().setText(rule), false
        );
        ability.setTargetAdjuster(MarshalsAnthemAdjuster.instance);
        this.addAbility(ability);
    }

    private MarshalsAnthem(final MarshalsAnthem card) {
        super(card);
    }

    @Override
    public MarshalsAnthem copy() {
        return new MarshalsAnthem(this);
    }
}

enum MarshalsAnthemAdjuster implements TargetAdjuster {
    instance;
    private static final FilterCard filter = new FilterCreatureCard("creature card in your graveyard");

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int numbTargets = MultikickerCount.instance.calculate(game, ability, null);
        if (numbTargets > 0) {
            ability.addTarget(new TargetCardInYourGraveyard(0, numbTargets, filter));
        }
    }
}