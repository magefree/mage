package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.TokenImpl;

/**
 *
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
        TriggeredAbility ability = new PutIntoGraveFromBattlefieldAllTriggeredAbility(new BecomesCreatureSourceEffect(new LurkingSkirgeToken(), "", Duration.WhileOnBattlefield, true, false), false, filter, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "When a creature is put into an opponent's graveyard from the battlefield, if {this} is an enchantment, {this} becomes a 3/2 Phyrexian Imp creature with flying."));
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

    public LurkingSkirgeToken(final LurkingSkirgeToken token) {
        super(token);
    }

    public LurkingSkirgeToken copy() {
        return new LurkingSkirgeToken(this);
    }
}
