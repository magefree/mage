
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.permanent.token.HippoToken;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class Phelddagrif extends CardImpl {

    public Phelddagrif(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHELDDAGRIF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {G}: Phelddagrif gains trample until end of turn. Target opponent creates a 1/1 green Hippo creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{G}"));
        ability.addEffect(new CreateTokenTargetEffect(new HippoToken()));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {W}: Phelddagrif gains flying until end of turn. Target opponent gains 2 life.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{W}"));
        ability.addEffect(new GainLifeTargetEffect(2));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {U}: Return Phelddagrif to its owner's hand. Target opponent may draw a card.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{U}"));
        ability.addEffect(new DrawCardTargetEffect(1, true));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private Phelddagrif(final Phelddagrif card) {
        super(card);
    }

    @Override
    public Phelddagrif copy() {
        return new Phelddagrif(this);
    }
}
