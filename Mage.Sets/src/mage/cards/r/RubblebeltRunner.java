package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RubblebeltRunner extends CardImpl {

    public static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public RubblebeltRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Rubblebelt Runner can't be blocked by creature tokens.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    private RubblebeltRunner(final RubblebeltRunner card) {
        super(card);
    }

    @Override
    public RubblebeltRunner copy() {
        return new RubblebeltRunner(this);
    }
}
