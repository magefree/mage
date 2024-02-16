package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrostaniThreeWhispers extends CardImpl {

    public TrostaniThreeWhispers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G/W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {1}{G}: Target creature gains deathtouch until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(DeathtouchAbility.getInstance()), new ManaCostsImpl<>("{1}{G}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {G/W}: Target creature gains vigilance until end of turn.
        ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(VigilanceAbility.getInstance()), new ManaCostsImpl<>("{G/W}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {2}{W}: Target creature gains double strike until end of turn.
        ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()), new ManaCostsImpl<>("{2}{W}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TrostaniThreeWhispers(final TrostaniThreeWhispers card) {
        super(card);
    }

    @Override
    public TrostaniThreeWhispers copy() {
        return new TrostaniThreeWhispers(this);
    }
}
