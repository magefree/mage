package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author Wehk
 */
public final class StoneSeederHierophant extends CardImpl {

    public StoneSeederHierophant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a land enters the battlefield under your control, untap Stone-Seeder Hierophant.
        this.addAbility(new LandfallAbility(new UntapSourceEffect()));

        // {tap}: Untap target land.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private StoneSeederHierophant(final StoneSeederHierophant card) {
        super(card);
    }

    @Override
    public StoneSeederHierophant copy() {
        return new StoneSeederHierophant(this);
    }
}
