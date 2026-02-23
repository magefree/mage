package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.Robot11Token;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaxterStockman extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("artifact creature you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public BaxterStockman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Baxter Stockman enters, create a 1/1 colorless Robot artifact creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Robot11Token())));

        // At the beginning of combat on your turn, target artifact creature you control gets +3/+0 and gains first strike and vigilance until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostTargetEffect(3, 0)
                .setText("target artifact creature you control gets +3/+0"));
        ability.addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())
                .setText("and gains first strike"));
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                .setText("and vigilance until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BaxterStockman(final BaxterStockman card) {
        super(card);
    }

    @Override
    public BaxterStockman copy() {
        return new BaxterStockman(this);
    }
}
