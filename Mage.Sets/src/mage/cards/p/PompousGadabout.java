package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PompousGadabout extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures that don't have a name");

    static {
        filter.add(PompousGadaboutPredicate.instance);
    }

    public PompousGadabout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Pompous Gadabout has hexproof as long as it's your turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "{this} has hexproof as long as it's your turn"
        )).addHint(MyTurnHint.instance));

        // Pompous Gadabout can't be blocked by creatures that don't have a name.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    private PompousGadabout(final PompousGadabout card) {
        super(card);
    }

    @Override
    public PompousGadabout copy() {
        return new PompousGadabout(this);
    }
}

enum PompousGadaboutPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getName() == null || input.getName().isEmpty();
    }
}