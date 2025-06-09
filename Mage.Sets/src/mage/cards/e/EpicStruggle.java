package mage.cards.e;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class EpicStruggle extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledCreaturePermanent("you control twenty or more creatures"),
            ComparisonType.MORE_THAN, 19
    );

    public EpicStruggle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // At the beginning of your upkeep, if you control twenty or more creatures, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect())
                .withInterveningIf(condition).addHint(CreaturesYouControlHint.instance));
    }

    private EpicStruggle(final EpicStruggle card) {
        super(card);
    }

    @Override
    public EpicStruggle copy() {
        return new EpicStruggle(this);
    }
}
