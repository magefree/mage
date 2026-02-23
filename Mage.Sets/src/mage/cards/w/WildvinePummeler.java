package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WildvinePummeler extends CardImpl {

    public WildvinePummeler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Vivid -- This spell costs {1} less to cast for each color among permanents you control.
        Ability ability = new SimpleStaticAbility(
            Zone.ALL,
            new SpellCostReductionForEachSourceEffect(1, ColorsAmongControlledPermanentsCount.ALL_PERMANENTS)
        );
        ability.setRuleAtTheTop(true);
        ability.setAbilityWord(AbilityWord.VIVID);
        ability.addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint());
        this.addAbility(ability);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private WildvinePummeler(final WildvinePummeler card) {
        super(card);
    }

    @Override
    public WildvinePummeler copy() {
        return new WildvinePummeler(this);
    }
}
