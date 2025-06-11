package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class LurkingSkirge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    private static final Condition condition = new SourceMatchesFilterCondition(new FilterEnchantmentPermanent("{this} is an enchantment"));

    public LurkingSkirge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // When a creature is put into an opponent's graveyard from the battlefield, if Lurking Skirge is an enchantment, Lurking Skirge becomes a 3/2 Imp creature with flying.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new BecomesCreatureSourceEffect(
                new LurkingSkirgeToken(), null, Duration.WhileOnBattlefield
        ), false, filter, false).withInterveningIf(condition)
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

class LurkingSkirgeToken extends TokenImpl {

    public LurkingSkirgeToken() {
        super("Phyrexian Imp", "3/2 Phyrexian Imp with flying.");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.IMP);
        power = new MageInt(3);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }

    private LurkingSkirgeToken(final LurkingSkirgeToken token) {
        super(token);
    }

    public LurkingSkirgeToken copy() {
        return new LurkingSkirgeToken(this);
    }
}
