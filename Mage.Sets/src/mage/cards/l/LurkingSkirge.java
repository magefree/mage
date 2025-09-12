package mage.cards.l;

import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.condition.common.SourceIsEnchantmentCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class LurkingSkirge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    public LurkingSkirge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // When a creature is put into an opponent's graveyard from the battlefield, if Lurking Skirge is an enchantment, Lurking Skirge becomes a 3/2 Imp creature with flying.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        3, 2, "3/2 Phyrexian Imp creature with flying", SubType.PHYREXIAN, SubType.IMP
                ).withAbility(FlyingAbility.getInstance()), null, Duration.WhileOnBattlefield
        ), false, filter, false)
                .withInterveningIf(SourceIsEnchantmentCondition.instance)
                .withRuleTextReplacement(true)
                .setTriggerPhrase("When a creature is put into an opponent's graveyard from the battlefield, "));
    }

    private LurkingSkirge(final LurkingSkirge card) {
        super(card);
    }

    @Override
    public LurkingSkirge copy() {
        return new LurkingSkirge(this);
    }
}
