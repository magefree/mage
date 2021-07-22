package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OvergrownArch extends CardImpl {

    public OvergrownArch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {T}: You gain 1 life.
        this.addAbility(new SimpleActivatedAbility(new GainLifeEffect(1), new TapSourceCost()));

        // {2}, Sacrifice Overgrown Arch: Learn.
        Ability ability = new SimpleActivatedAbility(new LearnEffect(), new GenericManaCost(2));
        ability.addCost(new SacrificeSourceCost());
        ability.addHint(OpenSideboardHint.instance);
        this.addAbility(ability);
    }

    private OvergrownArch(final OvergrownArch card) {
        super(card);
    }

    @Override
    public OvergrownArch copy() {
        return new OvergrownArch(this);
    }
}
