package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.ZombieWizardToken;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Loki
 */
public final class LichLordOfUnx extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ZOMBIE);
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Zombies you control", xValue);

    public LichLordOfUnx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new ZombieWizardToken()), new ManaCostsImpl<>("{U}{B}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleActivatedAbility(
                new LoseLifeTargetEffect(xValue)
                        .setText("target player loses X life"),
                new ManaCostsImpl<>("{U}{U}{B}{B}")
        );
        ability.addEffect(new MillCardsTargetEffect(xValue)
                .setText("and mills X cards, where X is the number of Zombies you control"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability.addHint(hint));
    }

    private LichLordOfUnx(final LichLordOfUnx card) {
        super(card);
    }

    @Override
    public LichLordOfUnx copy() {
        return new LichLordOfUnx(this);
    }
}
