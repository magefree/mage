package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FangbladeBrigand extends CardImpl {

    public FangbladeBrigand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.f.FangbladeEviscerator.class;

        // {1}{R}: Fangblade Brigand gets +1/+0 and gains first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        ).setText("{this} gets +1/+0"), new ManaCostsImpl<>("{1}{R}"));
        ability.addEffect(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        this.addAbility(ability);

        // Daybound
        this.addAbility(new TransformAbility());
        this.addAbility(new DayboundAbility());
    }

    private FangbladeBrigand(final FangbladeBrigand card) {
        super(card);
    }

    @Override
    public FangbladeBrigand copy() {
        return new FangbladeBrigand(this);
    }
}
