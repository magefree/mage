package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BanesInvoker extends CardImpl {

    public BanesInvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Wind Walk — {8}: Up to two target creatures each get +2/+2 and gain flying until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(2, 2).setText("up to two target creatures each get +2/+2"), new GenericManaCost(8));
        ability.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance()).setText("and gain flying until end of turn"));
        this.addAbility(ability.withFlavorWord("Wind Walk"));
    }

    private BanesInvoker(final BanesInvoker card) {
        super(card);
    }

    @Override
    public BanesInvoker copy() {
        return new BanesInvoker(this);
    }
}
