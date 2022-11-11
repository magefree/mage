package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class ElderOfLaurels extends CardImpl {

    public ElderOfLaurels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {3}{G}: Target creature gets +X/+X until end of turn, where X is the number of creatures you control.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
                new BoostTargetEffect(CreaturesYouControlCount.instance, CreaturesYouControlCount.instance, Duration.EndOfTurn),
                new ManaCostsImpl<>("{3}{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addHint(CreaturesYouControlHint.instance);
        this.addAbility(ability);
    }

    private ElderOfLaurels(final ElderOfLaurels card) {
        super(card);
    }

    @Override
    public ElderOfLaurels copy() {
        return new ElderOfLaurels(this);
    }
}
