package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeonardoWorldlyWarrior extends CardImpl {

    public LeonardoWorldlyWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This spell costs {1} less to cast for each creature you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, CreaturesYouControlCount.SINGULAR)
        ).addHint(CreaturesYouControlHint.instance));

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
    }

    private LeonardoWorldlyWarrior(final LeonardoWorldlyWarrior card) {
        super(card);
    }

    @Override
    public LeonardoWorldlyWarrior copy() {
        return new LeonardoWorldlyWarrior(this);
    }
}
