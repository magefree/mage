package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.WallColorlessToken;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuperSkrull extends CardImpl {

    public SuperSkrull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SKRULL);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{W}: Create a 0/4 colorless Wall creature token with defender.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new WallColorlessToken()), new ManaCostsImpl<>("{2}{W}")
        ));

        // {3}{G}: Super-Skrull gets +4/+4 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(4, 4, Duration.EndOfTurn), new ManaCostsImpl<>("{3}{G}")
        ));

        // {4}{R}: Super-Skrull deals 4 damage to target creature.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(4), new ManaCostsImpl<>("{4}{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {5}{U}: Target player draws four cards.
        ability = new SimpleActivatedAbility(new DrawCardTargetEffect(4), new ManaCostsImpl<>("{5}{U}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private SuperSkrull(final SuperSkrull card) {
        super(card);
    }

    @Override
    public SuperSkrull copy() {
        return new SuperSkrull(this);
    }
}
