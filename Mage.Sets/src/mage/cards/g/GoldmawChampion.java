package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.BoastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoldmawChampion extends CardImpl {

    public GoldmawChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Boast — {1}{W}: Tap target creature.
        Ability ability = new BoastAbility(new TapTargetEffect(), "{1}{W}");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GoldmawChampion(final GoldmawChampion card) {
        super(card);
    }

    @Override
    public GoldmawChampion copy() {
        return new GoldmawChampion(this);
    }
}
