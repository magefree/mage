package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SoldierToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class OkoyeDoraMilajeLeader extends CardImpl {

    private static final FilterCreaturePermanent filter
        = new FilterCreaturePermanent("attacking creature tokens");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(TokenPredicate.TRUE);
    }

    public OkoyeDoraMilajeLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Okoye enters, create two 1/1 white Soldier creature tokens.
        this.addAbility(new EntersBattlefieldAbility(
            new CreateTokenEffect(new SoldierToken(), 2)

        ));

        // Attacking creature tokens you control have first strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private OkoyeDoraMilajeLeader(final OkoyeDoraMilajeLeader card) {
        super(card);
    }

    @Override
    public OkoyeDoraMilajeLeader copy() {
        return new OkoyeDoraMilajeLeader(this);
    }
}
