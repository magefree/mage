package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NebelgastBeguiler extends CardImpl {

    public NebelgastBeguiler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // {W}, {T}: Tap target creature.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NebelgastBeguiler(final NebelgastBeguiler card) {
        super(card);
    }

    @Override
    public NebelgastBeguiler copy() {
        return new NebelgastBeguiler(this);
    }
}
