package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author nigelzor
 */
public final class ZuranEnchanter extends CardImpl {

    public ZuranEnchanter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{B}, {T}: Target player discards a card. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), new ManaCostsImpl<>("{2}{B}"), MyTurnCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);
    }

    private ZuranEnchanter(final ZuranEnchanter card) {
        super(card);
    }

    @Override
    public ZuranEnchanter copy() {
        return new ZuranEnchanter(this);
    }
}
